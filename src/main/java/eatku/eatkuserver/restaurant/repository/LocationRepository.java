package eatku.eatkuserver.restaurant.repository;

import eatku.eatkuserver.restaurant.domain.LectureBuilding;
import eatku.eatkuserver.restaurant.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String name);

}
