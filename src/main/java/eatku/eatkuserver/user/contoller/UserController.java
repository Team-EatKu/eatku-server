package eatku.eatkuserver.user.contoller;

import com.amazonaws.Response;
import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request);

}
