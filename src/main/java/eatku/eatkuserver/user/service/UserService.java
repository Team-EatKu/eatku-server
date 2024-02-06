package eatku.eatkuserver.user.service;

import com.amazonaws.services.kms.model.SignRequest;
import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;

public interface UserService {
    public LoginResponseDto login(LoginRequestDto request);
}
