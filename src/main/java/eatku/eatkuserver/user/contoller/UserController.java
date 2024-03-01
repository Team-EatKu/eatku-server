package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.user.dto.LikeListResponseDto;
import eatku.eatkuserver.user.dto.ReviewListResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserController {
    public ResponseEntity<ResultResponse> login(LoginRequestDto request);
    public ResponseEntity<ResultResponse> requestedMailSend(EmailSendRequestDto request);
    public ResponseEntity<ResultResponse> requestedMailAuth(EmailAuthRequestDto request);

    public ResponseEntity<ResultResponse> register(RegisterRequestDto request);
    public ResponseEntity<ResultResponse> getUserProfile(String token);
    public ResponseEntity<ResultResponse> modifyProfileImage(String token, MultipartFile image);

    public ResponseEntity<ResultResponse> getLikeList(String token);

    public ResponseEntity<ResultResponse> getReviewList(String token);

    public ResponseEntity<ResultResponse> emailCheck(String email);
    public ResponseEntity<ResultResponse> nickNameCheck(String nickName);
}
