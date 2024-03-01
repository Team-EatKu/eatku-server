package eatku.eatkuserver.user.contoller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.user.dto.emailauth.EmailAuthRequestDto;
import eatku.eatkuserver.user.dto.emailauth.EmailSendRequestDto;
import eatku.eatkuserver.user.dto.join.RegisterRequestDto;
import eatku.eatkuserver.user.dto.login.LoginRequestDto;
import eatku.eatkuserver.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "post login information and return token", description = "[@Operation] user login api")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody LoginRequestDto request) {
        System.out.println("UserControllerImpl.login");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LOGIN_SUCCESS, userService.login(request)));
    }

    @Override
    @Operation(summary = "send auth number to the email", description = "[@Operation] auth number request api")
    @PostMapping("/mail-send")
    public ResponseEntity<ResultResponse> requestedMailSend(@RequestBody EmailSendRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailSend");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_SEND_SUCCESS, userService.mailSend(request)));
    }

    @Override
    @Operation(summary = "authorization with auth number that you got from the email", description = "[@Operation] email auth api")
    @PostMapping("/mail-auth")
    public ResponseEntity<ResultResponse> requestedMailAuth(@RequestBody EmailAuthRequestDto request) {
        System.out.println("UserControllerImpl.requestedMailAuth");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_AUTH_SUCCESS, userService.mailAuth(request)));
    }

    @Override
    @Operation(summary = "post user's register information", description = "[@Operation] user register api")
    @PostMapping("/register")
    public ResponseEntity<ResultResponse> register(@RequestBody RegisterRequestDto request) {
        System.out.println("UserControllerImpl.register");
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_JOIN_SUCCESS, userService.join(request)));
    }

    @Override
    @Operation(summary = "get specific user's information", description = "[@Operation] get user's information api")
    @GetMapping("my-profile")
    public ResponseEntity<ResultResponse> getUserProfile(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_INFORMATION_SUCCESS, userService.getUserInformation(token)));
    }

    @Override
    @Operation(summary = "modify user's profile image", description = "[@Operation] user's profile image modify api")
    @PostMapping("/profile-img")
    public ResponseEntity<ResultResponse> modifyProfileImage(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_PROFILE_MODIFY_SUCCESS, userService.modifyProfileImage(token, image)));
    }

    @Override
    @Operation(summary = "get user's liked restaurant list", description = "[@Operation] get user's liked list api")
    @GetMapping("/likes")
    public ResponseEntity<ResultResponse> getLikeList(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LIKELIST_SUCCESS, userService.getUsersLikeList(token)));
    }

    @Override
    @Operation(summary = "get user's reviews", description = "[@Operation] get user's review list api")
    @GetMapping("/reviews")
    public ResponseEntity<ResultResponse> getReviewList(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_REVIEWLIST_SUCCESS, userService.getUsersReviewList(token)));
    }

    @Override
    @Operation(summary = "check email varidation", description = "[@Operation] email validation api")
    @GetMapping("/mail/{email}")
    public ResponseEntity<ResultResponse> emailCheck(@PathVariable String email) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EMAIL_CHECK_SUCCESS, userService.emailDuplicateCheck(email)));
    }

    @Override
    @Operation(summary = "check nickname varidation", description = "[@Operation] nickname varidation api")
    @GetMapping("/nickname/{nickName}")
    public ResponseEntity<ResultResponse> nickNameCheck(@PathVariable String nickName) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.NICKNAME_CHECK_SUCCESS, userService.nickNameValidationCheck(nickName)));
    }
}
