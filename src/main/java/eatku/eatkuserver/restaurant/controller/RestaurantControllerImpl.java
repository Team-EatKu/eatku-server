package eatku.eatkuserver.restaurant.controller;

import com.amazonaws.services.s3.AmazonS3;
import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import eatku.eatkuserver.restaurant.service.RestaurantService;
import eatku.eatkuserver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantControllerImpl implements RestaurantController{

    private final RestaurantService restaurantService;


    @Override
    @GetMapping("/{restaurantId}")
    public ResponseEntity<ResultResponse> restaurantInformation(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_INFORMATION_SUCCESS, restaurantService.getRestaurantInformation(restaurantId)));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<ResultResponse> searchRestaurants(@RequestBody RestaurantSearchRequestDto request) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_SEARCH_SUCCESS, restaurantService.searchRestaurants(request)));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ResultResponse> registerRestaurant(
            @RequestPart(value = "restaurant_data") RestaurantRegisterRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile profileImage) {

        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_REGISTER_SUCCESS, restaurantService.addRestaurant(request, profileImage)));
    }
}
