package eatku.eatkuserver.restaurant.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "search request dto")
public class RestaurantSearchRequestDto {
    @Schema(description = "restaurantName", example = "코코도리")
    private String restaurantName;
    @ArraySchema(arraySchema = @Schema(description = "해쉬태그"), schema = @Schema(implementation = String.class))
    private List<String> hashtagQuery;
    @ArraySchema(arraySchema = @Schema(description = "카테고리"), schema = @Schema(implementation = String.class))
    private List<String> categoryQuery;
    @ArraySchema(arraySchema = @Schema(description = "위치"), schema = @Schema(implementation = String.class))
    private List<String> locationQuery;
}
