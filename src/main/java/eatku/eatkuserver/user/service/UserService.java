package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.dto.LikeListResponseDto;
import eatku.eatkuserver.user.dto.ReviewListResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.dto.login.LoginResponseDto;

public interface UserService {
    public LoginResponseDto login(LoginRequestDto request);
    public String mailSend(EmailSendRequestDto request);
    public EmailAuthResponseDto mailAuth(EmailAuthRequestDto request);
    public String join(RegisterRequestDto request);

    public LikeListResponseDto getUsersLikeList(String token);

    public ReviewListResponseDto getUsersReviewList(String token);

    public String emailDuplicateCheck(String email);

    public String nickNameDuplicateCheck(String nickName);
}
