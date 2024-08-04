package travel.travel_community.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelPost;

import java.util.Optional;

public interface TravelPostRepository extends JpaRepository<TravelPost, Long> {

    Optional<TravelPost> findById(Long id);

}
