package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantListDto {
    private String title;
    private List<RestaurantDto> restaurantDtoList;
}
