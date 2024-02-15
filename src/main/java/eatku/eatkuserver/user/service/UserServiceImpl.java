package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.domain.Authority;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.domain.UserRole;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendResponseDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterResponseDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.dto.login.LoginResponseDto;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정정보입니다.")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return LoginResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .token(jwtProvider.createToken(user.getEmail(), user.getRoles()))
                .build();
    }

    @Override
    public EmailSendResponseDto mailSend(EmailSendRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if(user != null){
            return EmailSendResponseDto.builder()
                    .statusMessage("이미 가입한 회원입니다.")
                    .build();
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

        return EmailSendResponseDto.builder()
                .statusMessage("전송되었습니다.")
                .build();
    }

    @Override
    public EmailAuthResponseDto mailAuth(EmailAuthRequestDto request) {
        String email = request.getEmail();
        String authNumber = request.getAuthNumber();

        if(redisUtil.getData(authNumber).equals(email)){
            int newAuthNumber = makeRandomNumber();
            redisUtil.deleteData(authNumber);
            redisUtil.setData(Integer.toString(newAuthNumber), email);

            return EmailAuthResponseDto.builder()
                    .statustMessage("인증 성공")
                    .authNumber(Integer.toString(newAuthNumber))
                    .build();
        }else{
            return EmailAuthResponseDto.builder()
                    .statustMessage("인증 실패")
                    .authNumber(null)
                    .build();
        }
    }

    @Override   // 비밀번호 유효성 검사 해야함
    public RegisterResponseDto join(RegisterRequestDto request) {
        if(redisUtil.getData(request.getAuthNumber()).equals(request.getEmail())){
            User newUser = new User();
            newUser.setEmail(request.getEmail());

            List<Authority> authorityList = new ArrayList<>();
            Authority auth = new Authority();
            auth.setUserRole(UserRole.USER);
            authorityList.add(auth);
            newUser.setRoles(authorityList);

            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

            userRepository.save(newUser);

            redisUtil.deleteData(request.getAuthNumber());

            return RegisterResponseDto.builder()
                    .statusMessage("가입 성공")
                    .build();
        }else{
            return RegisterResponseDto.builder()
                    .statusMessage("인증번호가 잘못되었습니다.")
                    .build();
        }
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
