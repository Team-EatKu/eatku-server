package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class RestaurantRecommendResponseDto {
    private List<List<RestaurantDto>> restaurantData;
}
