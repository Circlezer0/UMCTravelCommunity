package travel.travel_community.repository.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import travel.travel_community.entity.mapping.ImageOfTravelItemPost;

import java.util.List;

public interface ImageItemPostMappingRepository extends JpaRepository<ImageOfTravelItemPost, Long> {

    @Query("SELECT DISTINCT ipm.image.id FROM ImageOfTravelItemPost ipm")
    List<Long> findAllImageIds();
}
