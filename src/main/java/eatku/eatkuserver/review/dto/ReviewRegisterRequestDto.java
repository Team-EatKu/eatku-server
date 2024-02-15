package eatku.eatkuserver.review.dto;

import lombok.Data;

@Data
public class ReviewRegisterRequestDto {
    private String content;
    private int scope;
    private Long restaurantId;
}
