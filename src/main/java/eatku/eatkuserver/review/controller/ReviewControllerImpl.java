package eatku.eatkuserver.review.controller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.review.dto.ReviewModifyRequestDto;
import eatku.eatkuserver.review.dto.ReviewRegisterRequestDto;
import eatku.eatkuserver.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ResultResponse> registerReview(@RequestBody ReviewRegisterRequestDto request, @RequestHeader("Authorization") String token, @RequestPart List<MultipartFile> images) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_REGISTER_SUCCESS, reviewService.addReview(request, token, images)));
    }

    @Override
    public ResponseEntity<ResultResponse> modifyReview(ReviewModifyRequestDto request) {
        return null;
    }

    @Override
    public ResponseEntity<ResultResponse> deleteReview(ReviewModifyRequestDto request) {
        return null;
    }
}
