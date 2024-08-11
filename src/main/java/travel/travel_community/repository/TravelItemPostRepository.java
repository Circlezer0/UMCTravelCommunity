package travel.travel_community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.categories.TravelItemCategory;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelItemPostRepository extends JpaRepository<TravelItemPost, Long> {
    @Query("SELECT tip FROM TravelItemPost tip WHERE tip.createdDate >= :startDate ORDER BY tip.viewCount DESC")
    List<TravelItemPost> findRecentTopViewedItemPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    // 최신순 조회 (createdDate 기준 내림차순)
    Page<TravelItemPost> findAllByOrderByCreatedDateDesc(Pageable pageable);
    Page<TravelItemPost> findAllByCategoriesOrderByCreatedDateDesc(Pageable pageable, List<TravelItemCategory> categories);


    // 오래된순 조회 (createdDate 기준 오름차순)
    Page<TravelItemPost> findAllByOrderByCreatedDateAsc(Pageable pageable);
    Page<TravelItemPost> findAllByCategoriesOrderByCreatedDateAsc(Pageable pageable, List<TravelItemCategory> categories);

    // 이름순 조회 (title 기준 오름차순)
    Page<TravelItemPost> findAllByOrderByTitleAsc(Pageable pageable);
    Page<TravelItemPost> findAllByCategoriesOrderByTitleAsc(Pageable pageable, List<TravelItemCategory> categories);

    // 좋아요순 조회 (likeCount 기준 내림차순)
    Page<TravelItemPost> findAllByOrderByLikeCountDesc(Pageable pageable);
    Page<TravelItemPost> findAllByCategoriesOrderByLikeCountDesc(Pageable pageable, List<TravelItemCategory> categories);

    // 스크랩순 조회 (scrapCount 기준 내림차순)
    Page<TravelItemPost> findAllByOrderByScrapCountDesc(Pageable pageable);
    Page<TravelItemPost> findAllByCategoriesOrderByScrapCountDesc(Pageable pageable, List<TravelItemCategory> categories);

    // 조회순 조회 (viewCount 기준 내림차순)
    Page<TravelItemPost> findAllByOrderByViewCountDesc(Pageable pageable);
    Page<TravelItemPost> findAllByCategoriesOrderByViewCountDesc(Pageable pageable, List<TravelItemCategory> categories);




    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds")
    Page<TravelItemPost> findByCategories(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "ORDER BY tip.createdDate DESC")
    Page<TravelItemPost> findByCategoriesOrderByCreatedDateDesc(Pageable pageable, @Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "ORDER BY tip.createdDate ASC")
    Page<TravelItemPost> findByCategoriesOrderByCreatedDateAsc(Pageable pageable, @Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "ORDER BY tip.title ASC")
    Page<TravelItemPost> findByCategoriesOrderByTitleAsc(Pageable pageable, @Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "ORDER BY tip.likeCount DESC")
    Page<TravelItemPost> findByCategoriesOrderByLikeCountDesc(Pageable pageable, @Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "ORDER BY tip.scrapCount DESC")
    Page<TravelItemPost> findByCategoriesOrderByScrapCountDesc(Pageable pageable, @Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT DISTINCT tip FROM TravelItemPost tip " +
            "JOIN tip.postCategories pc " +
            "WHERE pc.category.id IN :categoryIds " +
            "ORDER BY tip.viewCount DESC")
    Page<TravelItemPost> findByCategoriesOrderByViewCountDesc(Pageable pageable, @Param("categoryIds") List<Long> categoryIds);
}