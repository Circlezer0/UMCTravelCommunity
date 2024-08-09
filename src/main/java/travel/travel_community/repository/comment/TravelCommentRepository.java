package travel.travel_community.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelComment;
import travel.travel_community.entity.posts.TravelPost;

import java.util.List;
import java.util.Optional;

public interface TravelCommentRepository extends JpaRepository<TravelComment, Long> {

    List<TravelComment> findTravelCommentsByTravelPostAndParentTravelCommentIsNull(TravelPost post);
}
