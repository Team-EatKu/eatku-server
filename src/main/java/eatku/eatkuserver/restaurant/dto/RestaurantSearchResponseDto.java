package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantSearchResponseDto {
    private List<RestaurantDto> restaurantData;

}
