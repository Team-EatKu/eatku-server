package eatku.eatkuserver.user.dto;

import eatku.eatkuserver.restaurant.dto.RestaurantDto;
import lombok.Builder;

import java.util.List;

@Builder
public class LikeListResponseDto {
    private List<RestaurantDto> restaurantList;
}
