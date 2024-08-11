package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.posts.categories.TravelItemCategory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TravelItemCategoryRepository extends JpaRepository<TravelItemCategory, Long> {

    Optional<TravelItemCategory> findTravelItemCategoryByName(String name);

    boolean existsByName(String name);

    Collection<TravelItemCategory> findByNameIn(List<String> categoryNames);
}
