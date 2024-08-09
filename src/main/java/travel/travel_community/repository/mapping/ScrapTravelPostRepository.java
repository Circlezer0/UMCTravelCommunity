package travel.travel_community.repository.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.ScrapTravelPost;
import travel.travel_community.entity.posts.TravelPost;

public interface ScrapTravelPostRepository extends JpaRepository<ScrapTravelPost, Long> {
    boolean existsByPostAndUser(TravelPost post, User user);
}
