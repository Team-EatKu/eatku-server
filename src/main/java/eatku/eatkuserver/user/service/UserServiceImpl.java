package eatku.eatkuserver.user.service;

import eatku.eatkuserver.global.error.ErrorCode;
import eatku.eatkuserver.global.error.exception.EntityNotFoundException;
import eatku.eatkuserver.restaurant.domain.LectureBuilding;
import eatku.eatkuserver.restaurant.dto.RestaurantDto;
import eatku.eatkuserver.review.domain.Review;
import eatku.eatkuserver.review.dto.ReviewDto;
import eatku.eatkuserver.user.domain.Authority;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.domain.UserRole;
import eatku.eatkuserver.user.dto.LikeListResponseDto;
import eatku.eatkuserver.user.dto.ReviewListResponseDto;
import eatku.eatkuserver.user.dto.UserModifyRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.dto.login.LoginResponseDto;
import eatku.eatkuserver.user.repository.LectureBuildingRepository;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final LectureBuildingRepository lectureBuildingRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "해당 메일이 존재하지 않습니다.")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new EntityNotFoundException(ErrorCode.NOT_EQUAL_PASSWORD, "비밀번호가 틀렸습니다.");
        }

        return LoginResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .lectureBuilding(user.getLectureBuilding().getName())
                .token(jwtProvider.createToken(user.getEmail(), user.getRoles()))
                .build();
    }

    @Override
    @Transactional
    public String mailSend(EmailSendRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if(user != null){
            throw new EntityNotFoundException(ErrorCode.ALREADY_EXIST_EMAIL, "이미 존재하는 이메일입니다.");
        }

        int authNumber = makeRandomNumber();
        String setFrom = "leesung2925@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = request.getEmail();
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "나의 APP을 방문해주셔서 감사합니다." + 	//html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요"; //이메일 내용 삽입
        joinEmail(setFrom, toMail, title, content, authNumber);

        return "메일이 전송되었습니다.";
    }

    @Override
    @Transactional
    public EmailAuthResponseDto mailAuth(EmailAuthRequestDto request) {
        String email = request.getEmail();
        String authNumber = request.getAuthNumber();

        if(email == null || authNumber == null){
            throw new EntityNotFoundException(ErrorCode.MAIL_AUTH_FAILED, "이메일이나 인증번호가 입력되지 않았습니다.");
        }

        if(redisUtil.getData(authNumber).equals(email)){
            int newAuthNumber = makeRandomNumber();
            redisUtil.deleteData(authNumber);
            redisUtil.setData(Integer.toString(newAuthNumber), email);

            return EmailAuthResponseDto.builder()
                    .authNumber(Integer.toString(newAuthNumber))
                    .build();
        }else{
            throw new EntityNotFoundException(ErrorCode.MAIL_AUTH_FAILED, "인증번호가 일치하지 않습니다.");
        }
    }

    @Override   // 비밀번호 유효성 검사, 닉네임 중복체크 추가해야함
    @Transactional
    public String join(RegisterRequestDto request) {
        if(request.getEmail() == null || request.getPassword() == null ||
                request.getNickName() == null || request.getLectureBuilding() == null){
            throw new EntityNotFoundException(ErrorCode.USER_REGISTER_FAILED, "값이 부족합니다.");
        }
        if(redisUtil.getData(request.getAuthNumber()).equals(request.getEmail())){
            User newUser = new User();
            newUser.setEmail(request.getEmail());

            List<Authority> authorityList = new ArrayList<>();
            Authority auth = new Authority();
            auth.setUserRole(UserRole.USER);
            authorityList.add(auth);
            newUser.setRoles(authorityList);

            newUser.setLectureBuilding(lectureBuildingRepository.findByName(request.getLectureBuilding()).orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.USER_REGISTER_FAILED, "강의동 정보가 상이합니다.")
            ));

            newUser.setNickName(request.getNickName());

//            newUser.setLectureBuilding();

            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            userRepository.save(newUser);

            redisUtil.deleteData(request.getAuthNumber());

            return newUser.getEmail();
        }else{
            throw new EntityNotFoundException(ErrorCode.MAIL_AUTH_FAILED, "인증번호가 일치하지 않습니다.");
        }
    }

    @Override
    public String modify(UserModifyRequestDto request, String token) {

        return null;
    }

    @Override
    @Transactional
    public LikeListResponseDto getUsersLikeList(String token) {
        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );

        return LikeListResponseDto.builder()
                .restaurantList(user.getLikeList().stream()
                        .map(like -> {
                            return RestaurantDto.from(like.getRestaurant());
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public ReviewListResponseDto getUsersReviewList(String token) {
        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );

        return ReviewListResponseDto.builder()
                .reviewList(user.getReviewList().stream()
                        .map(ReviewDto::from)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public String emailDuplicateCheck(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if(user != null){
            throw new EntityNotFoundException(ErrorCode.ALREADY_EXIST_EMAIL, "이미 가입된 이메일입니다.");
        }
        return "사용 가능한 이메일입니다.";
    }

    @Override
    @Transactional
    public String nickNameDuplicateCheck(String nickName) {
        User user = userRepository.findByNickName(nickName).orElse(null);

        if(user != null){
            throw new EntityNotFoundException(ErrorCode.ALREADY_EXIST_NICKNAME, "이미 사용중인 닉네임입니다.");
        }
        return "사용 가능한 닉네임입니다.";
    }

//    public void joinTest(){
//        User user = new User();
//        user.setEmail("leesung2925@gmail.com");
//        user.setPassword(passwordEncoder.encode("123456"));
//        List<Authority> roles = new ArrayList<>();
//        Authority auth = new Authority();
//        auth.setUser(user);
//        auth.setUserRole(UserRole.USER);
//        roles.add(auth);
//        user.setRoles(roles);
//        user.setNickName("이성민");
//        userRepository.save(user);
//    }

    public int makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        return Integer.parseInt(randomNumber);
    }

    public void joinEmail(String setFrom, String toMail, String title, String content, int authNumber) {
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
        redisUtil.setDataExpire(Integer.toString(authNumber),toMail,60*5L);
    }

}
