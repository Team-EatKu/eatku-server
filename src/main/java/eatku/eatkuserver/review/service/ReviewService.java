package eatku.eatkuserver.review.service;

import eatku.eatkuserver.review.dto.ReviewModifyRequestDto;
import eatku.eatkuserver.review.dto.ReviewRegisterRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    public String addReview(ReviewRegisterRequestDto request, String token, List<MultipartFile> images);

    public String modifyReview(ReviewModifyRequestDto request, String token);

    public String deleteReview(Long reviewId, String token);
}
