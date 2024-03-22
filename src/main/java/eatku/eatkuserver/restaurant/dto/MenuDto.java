package eatku.eatkuserver.restaurant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class MenuDto {
    @Schema(description = "menu name", example = "어쩌규 규동")
    private String name;
    @Schema(description = "price of this menu", example = "6000")
    private Long price;
}
