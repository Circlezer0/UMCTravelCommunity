package travel.travel_community.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.travel_community.entity.posts.TravelItemPost;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelItemPostRepository extends JpaRepository<TravelItemPost, Long> {
    @Query("SELECT tip FROM TravelItemPost tip WHERE tip.createdDate >= :startDate ORDER BY tip.viewCount DESC")
    List<TravelItemPost> findRecentTopViewedItemPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}