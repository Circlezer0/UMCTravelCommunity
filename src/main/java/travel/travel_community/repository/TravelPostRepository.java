package travel.travel_community.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.travel_community.entity.posts.TravelPost;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelPostRepository extends JpaRepository<TravelPost, Long> {
    @Query("SELECT tp FROM TravelPost tp WHERE tp.createdDate >= :startDate ORDER BY tp.viewCount DESC")
    List<TravelPost> findRecentTopViewedPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}