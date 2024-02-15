package eatku.eatkuserver.restaurant.repository;

import eatku.eatkuserver.restaurant.domain.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByCategoryName(String categoryName);
}
