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
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/mail-send")
    public ResponseEntity<ResultResponse> requestedMailSend(@RequestBody EmailSendRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailSend");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_SEND_SUCCESS, userService.mailSend(request)));
    }

    @Override
    @PostMapping("/mail-auth")
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
    @PostMapping("/profile-img")
    public ResponseEntity<ResultResponse> modifyProfileImage(@RequestHeader("Authorization") String token, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_PROFILE_MODIFY_SUCCESS, userService.modifyProfileImage(token, image)));
    }

    @Override
    @GetMapping("/likes")
    public ResponseEntity<ResultResponse> getLikeList(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LIKELIST_SUCCESS, userService.getUsersLikeList(token)));
    }

    @Override
    @GetMapping("/reviews")
    public ResponseEntity<ResultResponse> getReviewList(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_REVIEWLIST_SUCCESS, userService.getUsersReviewList(token)));
    }

    @Override
    @GetMapping("/mail/{email}")
    public ResponseEntity<ResultResponse> emailCheck(@PathVariable String email) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_CHECK_SUCCESS, userService.emailDuplicateCheck(email)));
    }

    @Override
    @GetMapping("/nickname/{nickName}")
    public ResponseEntity<ResultResponse> nickNameCheck(@PathVariable String nickName) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.NICKNAME_CHECK_SUCCESS, userService.nickNameValidationCheck(nickName)));
    }
}
