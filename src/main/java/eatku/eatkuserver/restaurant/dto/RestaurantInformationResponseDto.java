package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.review.dto.ReviewDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@Schema
public class RestaurantInformationResponseDto {
    private Long restaurantId;
    private String name;
    private String address;
    private String phoneNumber;
    private Long likeCount;
    private double averageScope;
    private double latitude;
    private double longitude;
    private String information;
    private String startTime;
    private String endTime;
    private List<MenuDto> menuDtoList;
    private List<String> hashtagList;
    private List<String> categoryList;
    private List<ReviewDto> reviewList;
}
