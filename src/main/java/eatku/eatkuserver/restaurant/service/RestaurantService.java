package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {
    public RestaurantRegisterResponseDto addRestaurant(RestaurantRegisterRequestDto request, MultipartFile profileImage);

    public RestaurantSearchResponseDto searchRestaurants(RestaurantSearchRequestDto request);

    public RestaurantSearchResponseDto getRestaurantInformation(Long restaurantId);
}
