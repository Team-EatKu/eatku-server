package eatku.eatkuserver.review.controller;

import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.review.dto.ReviewModifyRequestDto;
import eatku.eatkuserver.review.dto.ReviewRegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewController {
    public ResponseEntity<ResultResponse> registerReview(ReviewRegisterRequestDto request, String token, List<MultipartFile> images);
    public ResponseEntity<ResultResponse> modifyReview(ReviewModifyRequestDto request);
    public ResponseEntity<ResultResponse> deleteReview(ReviewModifyRequestDto request);

}
