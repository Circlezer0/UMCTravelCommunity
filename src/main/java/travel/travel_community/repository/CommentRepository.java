package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.posts.Comment;
import travel.travel_community.entity.posts.TravelPost;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByTravelPostOrderByIdDesc(TravelPost travelPost);
}
