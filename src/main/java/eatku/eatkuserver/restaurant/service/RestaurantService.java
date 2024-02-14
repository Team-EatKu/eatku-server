package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {
    public RestaurantRegisterResponseDto addRestaurant(RestaurantRegisterRequestDto request, List<MultipartFile> images);
}
