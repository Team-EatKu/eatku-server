package eatku.eatkuserver.user.repository;

import eatku.eatkuserver.restaurant.domain.LectureBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureBuildingRepository extends JpaRepository<LectureBuilding, Long> {
    Optional<LectureBuilding> findByName(String name);
}
