package travel.travel_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.ScrappedPost;
import travel.travel_community.entity.posts.TravelPost;

public interface ScrappedPostRepository extends JpaRepository<ScrappedPost, Long> {
    boolean existsByUserAndPost(User user, TravelPost post);

    void deleteByUserAndPost(User user, TravelPost post);
}
