package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import travel.travel_community.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByUrl(String url);

    @Query("SELECT i.id FROM Image i")
    List<Long> findAllIds();

    Optional<Image> findImageByUrl(String url);
}
