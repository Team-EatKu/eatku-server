package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.domain.Authority;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.domain.UserRole;
import eatku.eatkuserver.user.dto.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.EmailSendResponseDto;
import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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
        return null;
    }

    public void joinTest(){
        User user = new User();
        user.setEmail("leesung2925@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        List<Authority> roles = new ArrayList<>();
        Authority auth = new Authority();
        auth.setUser(user);
        auth.setUserRole(UserRole.USER);
        roles.add(auth);
        user.setRoles(roles);
        user.setNickName("이성민");
        userRepository.save(user);
    }
}
