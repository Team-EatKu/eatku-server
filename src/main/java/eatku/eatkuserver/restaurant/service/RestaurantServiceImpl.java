package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.restaurant.domain.*;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantRegisterResponseDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchRequestDto;
import eatku.eatkuserver.restaurant.dto.RestaurantSearchResponseDto;
import eatku.eatkuserver.restaurant.repository.CategoryRepository;
import eatku.eatkuserver.restaurant.repository.HashTagRepository;
import eatku.eatkuserver.restaurant.repository.RestaurantRepository;
import eatku.eatkuserver.s3.service.S3Service;
import eatku.eatkuserver.user.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository rr;
    private final CategoryRepository cr;
    private final HashTagRepository hr;
    private final S3Service s3Service;

    @Override
    @Transactional
    public RestaurantRegisterResponseDto addRestaurant(RestaurantRegisterRequestDto request, MultipartFile profileImage) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setLocation(request.getLocation());
        restaurant.setInformation(request.getInformation());
        restaurant.setStartTime(request.getStartTime());
        restaurant.setEndTime(request.getEndTime());
        restaurant.setLatitude(request.getLatitude());
        restaurant.setLongitude(request.getLongitude());
        restaurant.setMenuList(request.getMenuList().stream()
                .map(menu -> {
                    menu.setRestaurant(restaurant);
                    return menu;
                })
                .collect(Collectors.toList()));

        restaurant.setCategoryList(request.getCategoryList().stream()
                .map(category -> {
                    Category findCg = cr.findCategoryByCategoryName(category.getCategoryName()).orElse(null);
                    if(findCg == null){
                        findCg = cr.save(category);
                    }
                    RestaurantCategory rc = new RestaurantCategory();
                    rc.setRestaurant(restaurant);
                    rc.setCategory(findCg);
                    return rc;
                })
                .collect(Collectors.toList()));

        restaurant.setHashtagList(request.getHashtagList().stream()
                .map(hashtag -> {
                    Hashtag findHashTag = hr.findHashtagByName(hashtag.getName()).orElse(null);
                    if(findHashTag == null){
                        findHashTag = hr.save(hashtag);
                    }
                    RestaurantHashtag rh = new RestaurantHashtag();
                    rh.setRestaurant(restaurant);
                    rh.setHashtag(findHashTag);
                    return rh;
                })
                .collect(Collectors.toList()));

        String profileImageUrl;
        try{
            profileImageUrl = s3Service.saveFile(profileImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        restaurant.setProfileImageUrl(profileImageUrl);

        rr.save(restaurant);

        return RestaurantRegisterResponseDto.builder()
                .statusMessage("저장 성공")
                .build();
    }

    @Override
    public RestaurantSearchResponseDto searchRestaurants(RestaurantSearchRequestDto request) {
        return null;
    }

    @Override
    public RestaurantSearchResponseDto getRestaurantInformation(Long restaurantId) {
        return null;
    }
}
