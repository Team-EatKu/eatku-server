package eatku.eatkuserver.user.dto;

import eatku.eatkuserver.review.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewListResponseDto {
    private List<ReviewDto> reviewList;
}
