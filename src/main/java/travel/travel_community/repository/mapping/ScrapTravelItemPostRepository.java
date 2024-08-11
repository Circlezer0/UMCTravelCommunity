package travel.travel_community.repository.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.ScrapTravelItemPost;
import travel.travel_community.entity.posts.TravelItemPost;

public interface ScrapTravelItemPostRepository extends JpaRepository<ScrapTravelItemPost, Long> {

    boolean existsByPostAndUser(TravelItemPost post, User user);
}
