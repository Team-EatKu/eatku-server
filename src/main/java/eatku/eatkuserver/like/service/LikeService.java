package eatku.eatkuserver.like.service;

public interface LikeService {
    public String likeOrDislikeRestaurant(Long restaurantId, String token);
}
