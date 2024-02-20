package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    public ResponseEntity<ResultResponse> login(LoginRequestDto request);
    public ResponseEntity<ResultResponse> requestedMailSend(EmailSendRequestDto request);
    public ResponseEntity<ResultResponse> requestedMailAuth(EmailAuthRequestDto request);

    public ResponseEntity<ResultResponse> register(RegisterRequestDto request);

    public ResponseEntity<ResultResponse> emailCheck(String email);
    public ResponseEntity<ResultResponse> nickNameCheck(String nickName);
}
