package travel.travel_community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.travel_community.entity.posts.TravelPost;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelPostRepository extends JpaRepository<TravelPost, Long> {

    // startDate ~ now 중 조회수가 많은 순서대로 게시글 조회
    @Query("SELECT tp FROM TravelPost tp WHERE tp.createdDate >= :startDate ORDER BY tp.viewCount DESC")
    List<TravelPost> findRecentTopViewedPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    // 최신순 조회 (createdDate 기준 내림차순)
    Page<TravelPost> findAllByOrderByCreatedDateDesc(Pageable pageable);

    // 오래된순 조회 (createdDate 기준 오름차순)
    Page<TravelPost> findAllByOrderByCreatedDateAsc(Pageable pageable);

    // 이름순 조회 (title 기준 오름차순)
    Page<TravelPost> findAllByOrderByTitleAsc(Pageable pageable);

    // 좋아요순 조회 (likeCount 기준 내림차순)
    Page<TravelPost> findAllByOrderByLikeCountDesc(Pageable pageable);

    // 조회순 조회 (viewCount 기준 내림차순)
    Page<TravelPost> findAllByOrderByViewCountDesc(Pageable pageable);
}