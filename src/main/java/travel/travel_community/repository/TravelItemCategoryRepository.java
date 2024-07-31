package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.posts.categories.TravelItemCategory;

public interface TravelItemCategoryRepository extends JpaRepository<TravelItemCategory, Long> {

}
