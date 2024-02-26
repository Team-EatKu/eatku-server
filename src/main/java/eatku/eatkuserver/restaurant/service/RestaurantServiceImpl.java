package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.global.error.ErrorCode;
import eatku.eatkuserver.global.error.exception.EntityNotFoundException;
import eatku.eatkuserver.restaurant.domain.*;
import eatku.eatkuserver.restaurant.dto.*;
import eatku.eatkuserver.restaurant.repository.CategoryRepository;
import eatku.eatkuserver.restaurant.repository.HashTagRepository;
import eatku.eatkuserver.restaurant.repository.LocationRepository;
import eatku.eatkuserver.restaurant.repository.RestaurantRepository;
import eatku.eatkuserver.review.domain.Review;
import eatku.eatkuserver.review.dto.ReviewDto;
import eatku.eatkuserver.s3.service.S3Service;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.dto.UserDto;
import eatku.eatkuserver.user.repository.UserRepository;
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
    private final LocationRepository lr;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public String addRestaurant(RestaurantRegisterRequestDto request, MultipartFile profileImage) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setLocation(lr.findByName(request.getLocation()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOT_FOUND_LOCATION, "잘못된 위치입니다.")
        ));
        restaurant.setAddress(request.getAddress());
        restaurant.setLocation(lr.findByName(request.getLocation()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOT_FOUND_LOCATION, "강의동의 이름이 상이합니다.")
        ));
        restaurant.setPhoneNumber(request.getPhoneNumber());
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
        if(!profileImage.isEmpty()){
            try{
                profileImageUrl = s3Service.saveFile(profileImage, "restaurant_profile_image");
            } catch (IOException e) {
                throw new EntityNotFoundException(ErrorCode.IMAGE_UPLOAD_FAILED, "이미지 업로드에 실패하였습니다.");
            }
            restaurant.setProfileImageUrl(profileImageUrl);
        }

        rr.save(restaurant);

        return "저장 성공";
    }

    @Override
    @Transactional
    public RestaurantSearchResponseDto searchRestaurants(RestaurantSearchRequestDto request, String token) {
        String restaurantName = request.getRestaurantName();
        List<String> hashtagQuery = request.getHashtagQuery();
        List<String> categoryQuery = request.getCategoryQuery();
        List<String> locationQuery = request.getLocationQuery();

        if(hashtagQuery.isEmpty() || categoryQuery.isEmpty() || locationQuery.isEmpty()){
            throw new EntityNotFoundException(ErrorCode.ILLEGER_SEARCH_PARAMETER, "검색에 필요한 정보가 부족합니다.");
        }

        List<Restaurant> searchRestaurantList = rr.findByHashtagsCategoriesLocationsAndName(restaurantName, categoryQuery, hashtagQuery, locationQuery);

        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );

        List<Restaurant> restaurantList = user.getLikeList().stream()
                .map(like -> {
                    return like.getRestaurant();
                }).collect(Collectors.toList());

        List<RestaurantDto> restaurantDtoList = searchRestaurantList.stream()
                .map(restaurant -> {

                    boolean isLiked = restaurantList.contains(restaurant);

                    return RestaurantDto.from(restaurant, isLiked);
                })
                .collect(Collectors.toList());

        return RestaurantSearchResponseDto.builder()
                .restaurantData(restaurantDtoList)
                .build();
    }

    @Override
    public RestaurantInformationResponseDto getRestaurantInformation(Long restaurantId, String token) {
        Restaurant restaurant = rr.findRestaurantById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.RESTAURANT_NOT_FOUND, restaurantId + " : 해당 id의 식당이 존재하지 않습니다.")
        );

        // 식당 별점 계산
        double averageScope = 0;

        if(!restaurant.getReiviewList().isEmpty()){
            int totalScope = 0;
            for(Review review : restaurant.getReiviewList()){
                totalScope += review.getScope();
            }
            averageScope = (double) totalScope / restaurant.getReiviewList().size();
        }

        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );


        List<Restaurant> restaurantList = user.getLikeList().stream()
                .map(like -> {
                    return like.getRestaurant();
                }).collect(Collectors.toList());

        boolean isLiked = restaurantList.contains(restaurant);

        return RestaurantInformationResponseDto.builder()
                .restaurantId(restaurantId)
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .phoneNumber(restaurant.getPhoneNumber())
                .likeCount((long) restaurant.getLikeList().size())
                .averageScope(averageScope)
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .information(restaurant.getInformation())
                .startTime(restaurant.getStartTime())
                .endTime(restaurant.getEndTime())
                .isLiked(isLiked)
                .menuDtoList(restaurant.getMenuList().stream()
                        .map(menu -> {
                            return MenuDto.builder()
                                    .name(menu.getName())
                                    .price(menu.getPrice())
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .categoryList(restaurant.getCategoryList().stream()
                        .map(restaurantCategory -> {
                            String categoryName = restaurantCategory.getCategory().getCategoryName();
                            return categoryName;
                        })
                        .collect(Collectors.toList()))
                .hashtagList(restaurant.getHashtagList().stream()
                        .map(restaurantHashtag -> {
                            String hashtag = restaurantHashtag.getHashtag().getName();
                            return hashtag;
                        })
                        .collect(Collectors.toList()))
                .reviewList(restaurant.getReiviewList().stream()
                        .map(review -> {
                            return ReviewDto.builder()
                                    .id(review.getId())
                                    .scope(review.getScope())
                                    .content(review.getContent())
                                    .imageUrls(review.getImageUrls())
                                    .user(UserDto.builder()
                                            .nickName(review.getUser().getNickName())
                                            .profileImage(review.getUser().getProfileImageUrl())
                                            .build())
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
