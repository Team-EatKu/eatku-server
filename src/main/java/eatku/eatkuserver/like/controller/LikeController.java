package eatku.eatkuserver.like.controller;

import eatku.eatkuserver.global.result.ResultResponse;
import org.springframework.http.ResponseEntity;

public interface LikeController {
    public ResponseEntity<ResultResponse> likeOrDislikeRestaurant(Long restaurantId, String token);
}
