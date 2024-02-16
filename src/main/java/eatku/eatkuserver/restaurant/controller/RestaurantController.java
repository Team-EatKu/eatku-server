package eatku.eatkuserver.restaurant.controller;

import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantController {
    public ResponseEntity<ResultResponse> restaurantInformation(Long restaurantId);
    public ResponseEntity<ResultResponse> searchRestaurants(RestaurantSearchRequestDto request);
    public ResponseEntity<ResultResponse> registerRestaurant(RestaurantRegisterRequestDto request, MultipartFile profileImage);
}
