package eatku.eatkuserver.restaurant.repository;

import eatku.eatkuserver.restaurant.domain.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN r.hashtagList rh " +
            "LEFT JOIN r.categoryList rc " +
            "LEFT JOIN r.location l " +
            "WHERE (:restaurantName IS NULL OR r.name LIKE CONCAT('%', :restaurantName, '%')) " +
            "OR (:hashtags IS NULL OR rh.hashtag.name IN :hashtags) " +
            "OR (:categories IS NULL OR rc.category.categoryName IN :categories) " +
            "OR (:locations IS NULL OR l.name IN :locations)")
    Page<Restaurant> findByHashtagsCategoriesLocationsAndName(@Param("restaurantName") String restaurantName,
                                                              @Param("hashtags") List<String> hashtags,
                                                              @Param("categories") List<String> categories,
                                                              @Param("locations") List<String> locations,
                                                              Pageable pageable);


    Optional<Restaurant> findRestaurantById(Long id);
}
