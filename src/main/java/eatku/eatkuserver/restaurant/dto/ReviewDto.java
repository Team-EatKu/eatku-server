package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.user.dto.UserSimple;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewDto {

    private Long id;
    private UserSimple user;
    private String content;
    private int scope;
    private List<String> imageUrls;

}
