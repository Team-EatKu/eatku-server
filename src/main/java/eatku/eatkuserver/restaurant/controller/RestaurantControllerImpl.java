package eatku.eatkuserver.restaurant.controller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import eatku.eatkuserver.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantControllerImpl implements RestaurantController {

    private final RestaurantService restaurantService;


    @Override
    @Operation(summary = "get main recommended restaurant data", description = "[@Operation] restaurant recommend api")
    @GetMapping("")
    public ResponseEntity<ResultResponse> recommendRestaurant(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_RECOMMEND_SUCCESS, restaurantService.recommendRestaurant(token)));
    }

    @Override
    @Operation(summary = "get specific restaurant information", description = "[@Operation] get specific restaurant information api")
    @GetMapping("/{restaurantId}")
    public ResponseEntity<ResultResponse> restaurantInformation(@Parameter(description = "restaurant id") Long restaurantId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_INFORMATION_SUCCESS, restaurantService.getRestaurantInformation(restaurantId, token)));
    }

    @Override
    @Operation(summary = "get search restaurant data", description = "[@Operation] get search api")
    @GetMapping("/search")
    public ResponseEntity<ResultResponse> searchRestaurants(@ParameterObject RestaurantSearchRequestDto request, @Parameter(hidden = true) @RequestHeader("Authorization") String token, @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_SEARCH_SUCCESS, restaurantService.searchRestaurants(request, token, pageable)));
    }

    @Override
    @Operation(summary = "register restaurant data", description = "[@Operation] restaurant register api")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> registerRestaurant(
            @Parameter(description = "Restaurant registration data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantRegisterRequestDto.class)))
            @RequestPart("restaurant_data") RestaurantRegisterRequestDto request,

            @Parameter(description = "Profile image file",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "image", required = false) MultipartFile profileImage) {

        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESTAURANT_REGISTER_SUCCESS, restaurantService.addRestaurant(request, profileImage)));

    }
}
