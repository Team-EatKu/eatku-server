package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.dto.*;

public interface UserService {
    public LoginResponseDto login(LoginRequestDto request);
    public EmailSendResponseDto mailSend(EmailSendRequestDto request);
    public EmailAuthResponseDto mailAuth(EmailAuthRequestDto request);
    public RegisterResponseDto join(RegisterRequestDto request);
}
