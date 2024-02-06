package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
