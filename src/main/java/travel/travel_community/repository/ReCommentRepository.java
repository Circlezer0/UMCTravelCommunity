package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.posts.Comment;
import travel.travel_community.entity.posts.ReComment;

public interface ReCommentRepository extends JpaRepository<ReComment,Long> {
}
