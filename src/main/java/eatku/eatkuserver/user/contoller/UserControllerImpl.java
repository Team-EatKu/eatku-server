package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.user.dto.*;
import eatku.eatkuserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        System.out.println("UserControllerImpl.login");
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @Override
    @PostMapping("/mailSend")
    public ResponseEntity<EmailSendResponseDto> requestedMailSend(@RequestBody EmailSendRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailSend");
        return new ResponseEntity<>(userService.mailSend(request), HttpStatus.OK);
    }

    @Override
    @PostMapping("/mailAuth")
    public ResponseEntity<EmailAuthResponseDto> requestedMailAuth(@RequestBody EmailAuthRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailAuth");
        return new ResponseEntity<>(userService.mailAuth(request), HttpStatus.OK);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto request) {
        System.out.println("UserControllerImpl.register");
        return new ResponseEntity<>(userService.join(request), HttpStatus.OK);
    }
}
