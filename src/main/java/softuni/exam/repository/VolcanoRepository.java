package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.LinkedHashSet;
import java.util.Optional;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano, Integer> {

    Optional<Volcano> findByName(String name);

    Optional<Volcano> findById(Long exploringVolcanoId);

    LinkedHashSet<Volcano> findByElevationGreaterThanAndIsActiveTrueOrderByElevationDesc(int elevation);
}
