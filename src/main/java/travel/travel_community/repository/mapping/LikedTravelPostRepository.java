package travel.travel_community.repository.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.LikedTravelPost;
import travel.travel_community.entity.posts.TravelPost;

public interface LikedTravelPostRepository extends JpaRepository<LikedTravelPost, Long> {

    boolean existsByPostAndUser(TravelPost post, User user);
}
