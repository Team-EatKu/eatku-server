package eatku.eatkuserver.restaurant.repository;

import eatku.eatkuserver.restaurant.domain.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findById(Long id);
}
