package eatku.eatkuserver.restaurant.repository;

import eatku.eatkuserver.restaurant.domain.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findRestaurantById(Long id);

    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "JOIN r.categoryList rc " +
            "JOIN r.hashtagList rh " +
            "WHERE rc.category.categoryName IN :categoryNames OR rh.hashtag.name IN :hashtagNames")
    Optional<List<Restaurant>> findByCategoriesAndHashtags(@Param("categoryNames") List<String> categoryNames,
                                                           @Param("hashtagNames") List<String> hashtagNames);
}
