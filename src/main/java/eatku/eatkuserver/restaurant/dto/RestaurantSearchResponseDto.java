package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class RestaurantSearchResponseDto {
    private Page<RestaurantDto> restaurantData;

}
