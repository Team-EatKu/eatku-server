package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;
import eatku.eatkuserver.user.dto.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.EmailSendResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request);
    public EmailSendResponseDto requestedMailSend(EmailSendRequestDto request);

}
