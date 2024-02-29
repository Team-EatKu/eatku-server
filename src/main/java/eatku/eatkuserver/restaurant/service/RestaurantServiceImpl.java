package eatku.eatkuserver.restaurant.service;

import eatku.eatkuserver.global.error.ErrorCode;
import eatku.eatkuserver.global.error.exception.EntityNotFoundException;
import eatku.eatkuserver.like.domain.Like;
import eatku.eatkuserver.restaurant.domain.*;
import eatku.eatkuserver.restaurant.dto.*;
import eatku.eatkuserver.restaurant.repository.CategoryRepository;
import eatku.eatkuserver.restaurant.repository.HashTagRepository;
import eatku.eatkuserver.restaurant.repository.LocationRepository;
import eatku.eatkuserver.restaurant.repository.RestaurantRepository;
import eatku.eatkuserver.review.domain.Review;
import eatku.eatkuserver.restaurant.dto.Recommendation;
import eatku.eatkuserver.review.dto.ReviewDto;
import eatku.eatkuserver.s3.service.S3Service;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.dto.UserDto;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public RestaurantRecommendResponseDto recommendRestaurant(String token) {
        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );

        // 유저의 강의동을 뽑아옴
        LectureBuilding userLectureBuilding = user.getLectureBuilding();

        // 유저의 찜한 레스토랑 리스트를 뽑아옴
        List<Restaurant> userLikedRestaurantList = user.getLikeList().stream()
                .map(Like::getRestaurant)
                .toList();

        // 랜덤으로 3개의 enum(Recommendation)을 가져옴
        List<Recommendation> recommendations = makeRandomRecommendation();

        // 레스토랑 리스트 dto로 변환
        List<RestaurantListDto> restaurantListDtos = recommendations.stream()
                .map(recommendation -> {
                    String title = recommendation.getTitle();
                    String hashtag = recommendation.getHashtag();

                    List<RestaurantDto> restaurantDtoList = rr.findRestaurantsByHashtagNameAndLectureBuildingLimit7(hashtag, userLectureBuilding.getId()).stream()
                            .map(restaurant -> {
                                boolean isLiked = userLikedRestaurantList.contains(restaurant);
                                return RestaurantDto.from(restaurant, isLiked);
                            })
                            .collect(Collectors.toList());

                    return RestaurantListDto.builder()
                            .title(title)
                            .restaurantDtoList(restaurantDtoList)
                            .build();
                })
                .toList();

        return RestaurantRecommendResponseDto.builder()
                .restaurantData(restaurantListDtos)
                .build();
    }

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
                    Menu newMenu = new Menu();
                    newMenu.setRestaurant(restaurant);
                    newMenu.setName(menu.getName());
                    newMenu.setPrice(menu.getPrice());
                    return newMenu;
                })
                .collect(Collectors.toList()));

        restaurant.setCategoryList(request.getCategoryList().stream()
                .map(category -> {
                    Category findCg = cr.findCategoryByCategoryName(category).orElse(null);
                    if(findCg == null){
                        Category newCategory = new Category();
                        newCategory.setCategoryName(category);
                        findCg = cr.save(newCategory);
                    }
                    RestaurantCategory rc = new RestaurantCategory();
                    rc.setRestaurant(restaurant);
                    rc.setCategory(findCg);
                    return rc;
                })
                .collect(Collectors.toList()));

        restaurant.setHashtagList(request.getHashtagList().stream()
                .map(hashtag -> {
                    Hashtag findHashTag = hr.findHashtagByName(hashtag).orElse(null);
                    if(findHashTag == null){
                        Hashtag newHashtag = new Hashtag();
                        newHashtag.setName(hashtag);
                        findHashTag = hr.save(newHashtag);
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
    public RestaurantSearchResponseDto searchRestaurants(RestaurantSearchRequestDto request, String token, Pageable pageable) {
        String restaurantName = request.getRestaurantName();
        List<String> hashtagQuery = request.getHashtagQuery();
        List<String> categoryQuery = request.getCategoryQuery();
        List<String> locationQuery = request.getLocationQuery();


        Page<Restaurant> searchRestaurantList = rr.findByHashtagsCategoriesLocationsAndName(restaurantName, categoryQuery, hashtagQuery, locationQuery, pageable);

        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );

        List<Restaurant> restaurantList = user.getLikeList().stream()
                .map(like -> {
                    return like.getRestaurant();
                }).collect(Collectors.toList());

        Page<RestaurantDto> restaurantDtoList = searchRestaurantList
                .map(restaurant -> {

                    boolean isLiked = restaurantList.contains(restaurant);

                    return RestaurantDto.from(restaurant, isLiked);
                });

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

    private List<Recommendation> makeRandomRecommendation() {
        int count = 3;
        List<Recommendation> recommendations = new ArrayList<>();
        Random r = new Random();

        for(int i = 0; i < count; i++){
            int random = r.nextInt() % 5 + 1;
            boolean flag = true;
            for(Recommendation recommendation : recommendations){
                if(recommendation.getCode() == random){
                    i--;
                    flag = false;
                    break;
                }
            }
            if(flag){
                recommendations.add(Recommendation.of(random));
            }
        }

        return recommendations;
    }
}
