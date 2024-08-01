package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.posts.regions.Continent;

import java.util.Optional;

public interface ContinentRepository extends JpaRepository<Continent, Long> {
    Optional<Continent> findCountryByName(String name);
}
