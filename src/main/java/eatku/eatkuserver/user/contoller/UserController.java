package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.user.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request);
    public ResponseEntity<EmailSendResponseDto> requestedMailSend(EmailSendRequestDto request);
    public ResponseEntity<EmailAuthResponseDto> requestedMailAuth(EmailAuthRequestDto request);

    public ResponseEntity<RegisterResponseDto> register(RegisterRequestDto request);
}
