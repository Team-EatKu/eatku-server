package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.restaurant.domain.Category;
import eatku.eatkuserver.restaurant.domain.Hashtag;
import eatku.eatkuserver.restaurant.domain.Menu;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@Schema
public class RestaurantRegisterRequestDto {
    @Schema(description = "restaurant name", example = "코코도리")
    private String name;
    @Schema(description = "restaurant address", example = "서울특별시 광진구 화양동 123-123")
    private String address;
    @Schema(description = "restaurant location", example = "후문")
    private String location;
    @Schema(description = "restaurant phone number", example = "010-1234-1234")
    private String phoneNumber;
    @Schema(description = "restaurant latitude", example = "123.123")
    private double latitude;
    @Schema(description = "restaurant longitude", example = "123.123")
    private double longitude;
    @Schema(description = "restaurant information", example = "주차가능 어쩌구 저쩌구,,\n충전이 어쩌구 저쩌구..")
    private String information;
    @Schema(description = "restaurant daily start time", example = "17:00")
    private String startTime;
    @Schema(description = "restaurant daily end time", example = "24:00")
    private String endTime;
    @ArraySchema(arraySchema = @Schema(description = "menu list"), schema = @Schema(implementation = MenuDto.class))
    private List<MenuDto> menuList;
    @ArraySchema(arraySchema = @Schema(description = "category list"), schema = @Schema(implementation = String.class))
    private List<String> categoryList;
    @ArraySchema(arraySchema = @Schema(description = "hashtag list"), schema = @Schema(implementation = String.class))
    private List<String> hashtagList;
}
