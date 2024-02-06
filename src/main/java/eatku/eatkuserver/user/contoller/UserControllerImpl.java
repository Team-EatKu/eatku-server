package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.user.dto.LoginRequestDto;
import eatku.eatkuserver.user.dto.LoginResponseDto;
import eatku.eatkuserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request) {
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }
}
