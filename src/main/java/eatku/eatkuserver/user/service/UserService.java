package eatku.eatkuserver.user.service;

import eatku.eatkuserver.user.dto.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.EmailSendResponseDto;
import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;

public interface UserService {
    public LoginResponseDto login(LoginRequestDto request);
    public EmailSendResponseDto mailSend(EmailSendRequestDto request);
}
