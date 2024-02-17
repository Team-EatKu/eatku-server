package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto {
    private String name;
    private Long price;
}
