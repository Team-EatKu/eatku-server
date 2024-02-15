package eatku.eatkuserver.review.dto;

import lombok.Data;

@Data
public class ReviewModifyRequestDto {
    private Long reviewId;
    private String content;
    private int scope;
}
