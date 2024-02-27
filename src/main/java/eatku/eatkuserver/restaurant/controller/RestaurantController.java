package eatku.eatkuserver.restaurant.controller;

import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantController {
    public ResponseEntity<ResultResponse> restaurantInformation(Long restaurantId, String token);
    public ResponseEntity<ResultResponse> searchRestaurants(RestaurantSearchRequestDto request, String token, Pageable pageable);
    public ResponseEntity<ResultResponse> registerRestaurant(RestaurantRegisterRequestDto request, MultipartFile profileImage);
}
