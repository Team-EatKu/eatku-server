package eatku.eatkuserver.restaurant.controller;

import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantController {
    void restaurantInformation();
    void searchRestaurants();
    public ResponseEntity<RestaurantRegisterResponseDto> registerRestaurant(RestaurantRegisterRequestDto request, List<MultipartFile> images);
}
