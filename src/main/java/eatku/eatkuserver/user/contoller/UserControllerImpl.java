package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    @Override
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody LoginRequestDto request) {
        System.out.println("UserControllerImpl.login");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LOGIN_SUCCESS, userService.login(request)));
    }

    @Override
    @PostMapping("/mailSend")
    public ResponseEntity<ResultResponse> requestedMailSend(@RequestBody EmailSendRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailSend");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_SEND_SUCCESS, userService.mailSend(request)));
    }

    @Override
    @PostMapping("/mailAuth")
    public ResponseEntity<ResultResponse> requestedMailAuth(@RequestBody EmailAuthRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailAuth");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_AUTH_SUCCESS, userService.mailAuth(request)));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ResultResponse> register(@RequestBody RegisterRequestDto request) {
        System.out.println("UserControllerImpl.register");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_JOIN_SUCCESS, userService.join(request)));
    }

    @Override
    @GetMapping("/likes")
    public ResponseEntity<ResultResponse> getLikeList(String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LIKELIST_SUCCESS, userService.getUsersLikeList(token)));
    }

    @Override
    @GetMapping("/reviews")
    public ResponseEntity<ResultResponse> getReviewList(String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_REVIEWLIST_SUCCESS, userService.getUsersReviewList(token)));
    }

    @Override
    @GetMapping("/mail")
    public ResponseEntity<ResultResponse> emailCheck(String email) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_CHECK_SUCCESS, userService.emailDuplicateCheck(email)));
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<ResultResponse> nickNameCheck(String nickName) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.NICKNAME_CHECK_SUCCESS, userService.nickNameDuplicateCheck(nickName)));
    }
}
