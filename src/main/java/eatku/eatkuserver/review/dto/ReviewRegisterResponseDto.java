package eatku.eatkuserver.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRegisterResponseDto {
    private String statusMessage;
}
