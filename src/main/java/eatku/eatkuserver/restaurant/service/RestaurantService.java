package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.restaurant.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {
    public String addRestaurant(RestaurantRegisterRequestDto request, MultipartFile profileImage);

    public RestaurantSearchResponseDto searchRestaurants(RestaurantSearchRequestDto request, String token);

    public RestaurantInformationResponseDto getRestaurantInformation(Long restaurantId, String token);
}
