package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.review.domain.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
public class RestaurantInformationResponseDto {
    private Long restaurantId;
    private String name;
    private String location;
    private Long likeCount;
    private double averageScope;
    private Long latitude;
    private Long longitude;
    private String information;
    private String startTime;
    private String endTime;
    private List<MenuSimple> menuSimpleList;
    private List<String> hashtagList;
    private List<String> categoryList;
    private List<ReviewSimple> reviewList;
}
