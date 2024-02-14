package eatku.eatkuserver.restaurant.controller;

import com.amazonaws.services.s3.AmazonS3;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import eatku.eatkuserver.restaurant.service.RestaurantService;
import eatku.eatkuserver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantControllerImpl implements RestaurantController{
    private final S3Service s3Service;
    private final RestaurantService restaurantService;
    @Override
    public void restaurantInformation() {

    }

    @Override
    public void searchRestaurants() {

    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<RestaurantRegisterResponseDto> registerRestaurant(
            @RequestPart(value = "restaurant_data") RestaurantRegisterRequestDto request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        return new ResponseEntity<>(restaurantService.addRestaurant(request, images), HttpStatus.OK);
    }
}
