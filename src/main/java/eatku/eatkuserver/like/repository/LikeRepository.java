package eatku.eatkuserver.like.repository;

import eatku.eatkuserver.like.domain.Like;
import eatku.eatkuserver.restaurant.domain.Restaurant;
import eatku.eatkuserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findLikeByUserAndRestaurant(User user, Restaurant restaurant);
}
