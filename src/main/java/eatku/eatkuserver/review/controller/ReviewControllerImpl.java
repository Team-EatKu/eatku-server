package eatku.eatkuserver.review.controller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.review.dto.ReviewModifyRequestDto;
import eatku.eatkuserver.review.dto.ReviewRegisterRequestDto;
import eatku.eatkuserver.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewControllerImpl implements ReviewController{

    private final ReviewService reviewService;

    @Override
    @PostMapping()
    public ResponseEntity<ResultResponse> registerReview(@RequestPart ReviewRegisterRequestDto request, @RequestHeader("Authorization") String token, @RequestPart List<MultipartFile> images) {
//        System.out.println("request = " + request);
//        System.out.println("token = " + token);
//        System.out.println("images = " + images);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_REGISTER_SUCCESS, reviewService.addReview(request, token, images)));
    }

    @Override
    @PatchMapping()
    public ResponseEntity<ResultResponse> modifyReview(@RequestBody ReviewModifyRequestDto request, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_MODIFY_SUCCESS, reviewService.modifyReview(request, token)));
    }

    @Override
    @DeleteMapping()
    public ResponseEntity<ResultResponse> deleteReview(@RequestBody ReviewModifyRequestDto request, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_DELETE_SUCCESS, reviewService.deleteReview(request, token)));
    }
}
