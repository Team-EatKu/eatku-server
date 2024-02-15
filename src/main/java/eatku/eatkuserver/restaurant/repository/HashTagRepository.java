package eatku.eatkuserver.restaurant.repository;

import eatku.eatkuserver.restaurant.domain.Hashtag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface HashTagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findHashtagByName(String name);
}
