package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.posts.regions.Continent;

public interface ContinentRepository extends JpaRepository<Continent, Long> {
}
