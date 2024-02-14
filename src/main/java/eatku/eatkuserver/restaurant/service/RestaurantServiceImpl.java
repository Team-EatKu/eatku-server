package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.restaurant.domain.Menu;
import eatku.eatkuserver.restaurant.domain.Restaurant;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import eatku.eatkuserver.user.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{
    @Override
    public RestaurantRegisterResponseDto addRestaurant(RestaurantRegisterRequestDto request, List<MultipartFile> images) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setLocation(request.getLocation());
        restaurant.setEndTime(request.getEndTime());
        restaurant.setLatitude(request.getLatitude());
        restaurant.setLongitude(request.getLongitude());
        restaurant.setMenuList(request.getMenuList().stream()
                .map(menu -> {
                        menu.setRestaurant(restaurant);
                        return menu;
                })
                .collect(Collectors.toList()));
        return null;
    }
}
