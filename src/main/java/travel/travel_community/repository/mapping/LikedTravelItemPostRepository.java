package travel.travel_community.repository.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.LikedTravelItemPost;
import travel.travel_community.entity.posts.TravelItemPost;

public interface LikedTravelItemPostRepository extends JpaRepository<LikedTravelItemPost, Long> {

    boolean existsByPostAndUser(TravelItemPost post, User user);
}
