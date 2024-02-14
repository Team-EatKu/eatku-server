package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantRegisterResponseDto {
    private String statusMessage;
}
