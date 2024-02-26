package eatku.eatkuserver.user.dto;

import eatku.eatkuserver.restaurant.dto.RestaurantDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LikeListResponseDto {
    private List<RestaurantDto> restaurantList;
}
