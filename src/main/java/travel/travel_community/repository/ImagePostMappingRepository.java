package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import travel.travel_community.entity.mapping.ImageOfTravelPost;

import java.util.List;

public interface ImagePostMappingRepository extends JpaRepository<ImageOfTravelPost, Long> {
    @Query("SELECT DISTINCT ipm.image.id FROM ImageOfTravelPost ipm")
    List<Long> findAllImageIds();
}