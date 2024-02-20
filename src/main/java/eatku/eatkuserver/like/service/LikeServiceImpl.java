package eatku.eatkuserver.like.service;

import eatku.eatkuserver.global.error.ErrorCode;
import eatku.eatkuserver.global.error.exception.EntityNotFoundException;
import eatku.eatkuserver.like.domain.Like;
import eatku.eatkuserver.like.repository.LikeRepository;
import eatku.eatkuserver.restaurant.domain.Restaurant;
import eatku.eatkuserver.restaurant.repository.RestaurantRepository;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public String likeOrDislikeRestaurant(Long restaurantId, String token) {

        User user = userRepository.findByEmail(jwtProvider.getAccount(token)).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "잘못된 접근입니다.")
        );

        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.RESTAURANT_NOT_FOUND, "존재하지 않는 식당입니다.")
        );

        Like like = likeRepository.findLikeByUserAndRestaurant(user, restaurant).orElse(null);

        // 이미 좋아요를 했을 때
        if(like != null) {
            likeRepository.delete(like);
            return "찜하기 취소";
        }
        else{   // 안 했을 때
            like = new Like();
            like.setUser(user);
            like.setRestaurant(restaurant);
            likeRepository.save(like);
            return "찜하기 성공";
        }
    }
}
