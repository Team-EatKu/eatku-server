package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
public class RestaurantRecommendResponseDto {
    private List<List<RestaurantRepresentationData>> restaurantData;
}
