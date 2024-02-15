package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendResponseDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterResponseDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.dto.login.LoginResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request);
    public ResponseEntity<EmailSendResponseDto> requestedMailSend(EmailSendRequestDto request);
    public ResponseEntity<EmailAuthResponseDto> requestedMailAuth(EmailAuthRequestDto request);

    public ResponseEntity<RegisterResponseDto> register(RegisterRequestDto request);
}
