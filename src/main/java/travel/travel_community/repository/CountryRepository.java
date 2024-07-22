package travel.travel_community.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import travel.travel_community.entity.posts.regions.Country;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT c, COUNT(tp) as postCount FROM Country c LEFT JOIN TravelPost tp ON tp.country = c GROUP BY c ORDER BY COUNT(tp) DESC")
    List<Object[]> findTopCountriesByPostCount(Pageable pageable);
}