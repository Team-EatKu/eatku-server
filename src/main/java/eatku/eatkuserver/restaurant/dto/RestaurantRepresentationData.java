package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.restaurant.domain.Hashtag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantRepresentationData {
    private Long restaurantId;
    private String profileImage;
    private String name;
    private List<String> hashtagList;
    private double averageScope;
}
