package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.LikedPost;
import travel.travel_community.entity.posts.TravelPost;

public interface LikedPostRepository extends JpaRepository<LikedPost, Long> {
    boolean existsByUserAndPost(User user, TravelPost post);
    //삭제
    void deleteByUserAndPost(User user, TravelPost post);

}
