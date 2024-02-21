package eatku.eatkuserver.user.dto;

import eatku.eatkuserver.review.dto.ReviewDto;
import lombok.Builder;

import java.util.List;

@Builder
public class ReviewListResponseDto {
    private List<ReviewDto> reviewList;
}
