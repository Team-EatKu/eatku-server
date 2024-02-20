package eatku.eatkuserver.restaurant.dto;

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

}
