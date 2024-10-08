package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.restaurant.domain.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RestaurantDto {
    private Long restaurantId;
    private String profileImage;
    private String name;
    private String location;
    private List<String> hashtagList;
    private double averageScope;
    private boolean isLiked;
    public static RestaurantDto from(Restaurant restaurant, boolean isLiked) {
        return RestaurantDto.builder()
                .restaurantId(restaurant.getId())
                .profileImage(restaurant.getProfileImageUrl())
                .name(restaurant.getName())
                .location(restaurant.getLocation().getName())
                .hashtagList(restaurant.getHashtagList().stream()
                        .map(restaurantHashtag -> {
                            return restaurantHashtag.getHashtag().getName();
                        })
                        .collect(Collectors.toList()))
                .averageScope(restaurant.getAverageScope())
                .isLiked(isLiked)
                .build();
    }
}
