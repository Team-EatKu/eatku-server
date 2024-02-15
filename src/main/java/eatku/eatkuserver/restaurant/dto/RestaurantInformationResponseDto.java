package eatku.eatkuserver.restaurant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantInformationResponseDto {
    private Long restaurantId;
    private String name;
    private String location;
    private Long latitude;
    private Long longitude;
    private String information;
    private String startTime;
    private String endTime;
    private List<Menu>
    private List<String> hashtagList;
    private List<String> categoryList;
    private List<>
}
