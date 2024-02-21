package eatku.eatkuserver.like.controller;

import eatku.eatkuserver.global.result.ResultCode;
import eatku.eatkuserver.global.result.ResultResponse;
import eatku.eatkuserver.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeControllerImpl implements LikeController{

    private final LikeService likeService;

    @Override
    @PostMapping("/{restaurantId}")
    public ResponseEntity<ResultResponse> likeOrDislikeRestaurant(@PathVariable Long restaurantId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.LIKE_SAVE_OR_DELETE_SUCCESS, likeService.likeOrDislikeRestaurant(restaurantId, token)));
    }
}
