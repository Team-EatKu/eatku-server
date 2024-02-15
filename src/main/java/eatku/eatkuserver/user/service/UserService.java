package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthResponseDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendResponseDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterResponseDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.dto.login.LoginResponseDto;

public interface UserService {
    public LoginResponseDto login(LoginRequestDto request);
    public EmailSendResponseDto mailSend(EmailSendRequestDto request);
    public EmailAuthResponseDto mailAuth(EmailAuthRequestDto request);
    public RegisterResponseDto join(RegisterRequestDto request);
}
