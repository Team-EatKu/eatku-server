package eatku.eatkuserver.restaurant.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantSearchRequestDto {
    private String restaurantName;
    private List<String> hashtagQuery;
    private List<String> categoryQuery;
    private List<String> locationQuery;
}
