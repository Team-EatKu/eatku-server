package eatku.eatkuserver.restaurant.controller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import eatku.eatkuserver.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantControllerImpl implements RestaurantController{

    private final RestaurantService restaurantService;


    @Override
    @GetMapping("/{restaurantId}")
    public ResponseEntity<ResultResponse> restaurantInformation(@Parameter(description = "asdfasdf") Long restaurantId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_INFORMATION_SUCCESS, restaurantService.getRestaurantInformation(restaurantId, token)));
    }

    @Override
    @Operation(summary = "get search restaurant data", description = "[@Operation] get search api", security = {})
    @GetMapping("/search")
    public ResponseEntity<ResultResponse> searchRestaurants(@ParameterObject RestaurantSearchRequestDto request, @Parameter(hidden = true) @RequestHeader("Authorization") String token, @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_SEARCH_SUCCESS, restaurantService.searchRestaurants(request, token, pageable)));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ResultResponse> registerRestaurant(
            @RequestPart(value = "restaurant_data") RestaurantRegisterRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile profileImage) {

        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_REGISTER_SUCCESS, restaurantService.addRestaurant(request, profileImage)));
    }
}
