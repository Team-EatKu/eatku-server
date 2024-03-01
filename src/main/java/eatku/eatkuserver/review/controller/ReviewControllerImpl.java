package eatku.eatkuserver.review.controller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.review.dto.ReviewModifyRequestDto;
import eatku.eatkuserver.review.dto.ReviewRegisterRequestDto;
import eatku.eatkuserver.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.http.MediaType;
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
    @Operation(summary = "post user's restaurant review", description = "[@Operation] review register api")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> registerReview(
            @Parameter(description = "review registration data",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReviewRegisterRequestDto.class)))
            @RequestPart("review_register_data") ReviewRegisterRequestDto request,

            @Parameter(description = "Review images",
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "images", required = false) List<MultipartFile> images,

            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_REGISTER_SUCCESS, reviewService.addReview(request, token, images)));
    }

    @Override
    @Operation(summary = "patch user's restaurant review", description = "[@Operation] review patch api")
    @PatchMapping()
    public ResponseEntity<ResultResponse> modifyReview(@RequestBody ReviewModifyRequestDto request, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_MODIFY_SUCCESS, reviewService.modifyReview(request, token)));
    }

    @Override
    @Operation(summary = "delete user's restaurant review", description = "[@Operation] review delete api")
    @DeleteMapping()
    public ResponseEntity<ResultResponse> deleteReview(@RequestBody ReviewModifyRequestDto request, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REVIEW_DELETE_SUCCESS, reviewService.deleteReview(request, token)));
    }
}
