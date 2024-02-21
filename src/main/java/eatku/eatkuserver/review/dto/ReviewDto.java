package eatku.eatkuserver.review.dto;

import eatku.eatkuserver.review.domain.Review;
import eatku.eatkuserver.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewDto {

    private Long id;
    private UserDto user;
    private String content;
    private int scope;
    private List<String> imageUrls;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .user(UserDto.from(review.getUser()))
                .scope(review.getScope())
                .imageUrls(review.getImageUrls())
                .build();
    }

}
