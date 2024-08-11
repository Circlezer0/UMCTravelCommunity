package travel.travel_community.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.PostHandler;
import travel.travel_community.entity.Image;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.categories.TravelItemCategory;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.repository.TravelItemPostRepository;
import travel.travel_community.repository.mapping.LikedTravelItemPostRepository;
import travel.travel_community.repository.mapping.ScrapTravelItemPostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TravelItemPostService {

    private final TravelItemPostRepository travelItemPostRepository;
    private final TravelItemPostCategoryService travelItemPostCategoryService;
    private final LikedTravelItemPostRepository likedTravelItemPostRepository;
    private final ScrapTravelItemPostRepository scrapTravelItemPostRepository;
    private final ImageService imageService;

    private static final int PAGE_SIZE = 16; // 한 페이지에 보여줄 게시글 수

    public int getAllPostCount(){
        return travelItemPostRepository.findAll().size();
    }
    public List<TravelItemPost> getTopTravelItemPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelItemPostRepository.findRecentTopViewedItemPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }


    public Page<TravelItemPost> getLatestPosts(int page, List<String> categoriesName) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (categoriesName.getFirst().equals("전체")) {
            return travelItemPostRepository.findAllByOrderByCreatedDateDesc(pageable);
        }
        List<Long> categoryIdsByNames = travelItemPostCategoryService.findCategoryIdsByNames(categoriesName);
        return travelItemPostRepository.findByCategoriesOrderByCreatedDateDesc(pageable, categoryIdsByNames);
    }

    public Page<TravelItemPost> getOldestPosts(int page, List<String> categoriesName) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (categoriesName.getFirst().equals("전체")) {
            return travelItemPostRepository.findAllByOrderByCreatedDateAsc(pageable);
        }
        List<Long> categoryIdsByNames = travelItemPostCategoryService.findCategoryIdsByNames(categoriesName);
        return travelItemPostRepository.findByCategoriesOrderByCreatedDateDesc(pageable, categoryIdsByNames);
    }

    public Page<TravelItemPost> getMostViewedPosts(int page, List<String> categoriesName) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (categoriesName.getFirst().equals("전체")) {
            return travelItemPostRepository.findAllByOrderByViewCountDesc(pageable);
        }
        List<Long> categoryIdsByNames = travelItemPostCategoryService.findCategoryIdsByNames(categoriesName);
        return travelItemPostRepository.findByCategoriesOrderByViewCountDesc(pageable, categoryIdsByNames);
    }

    public Page<TravelItemPost> getMostLikedPosts(int page, List<String> categoriesName) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (categoriesName.getFirst().equals("전체")) {
            return travelItemPostRepository.findAllByOrderByLikeCountDesc(pageable);
        }
        List<Long> categoryIdsByNames = travelItemPostCategoryService.findCategoryIdsByNames(categoriesName);
        return travelItemPostRepository.findByCategoriesOrderByLikeCountDesc(pageable, categoryIdsByNames);
    }

    public Page<TravelItemPost> getMostScrapedPosts(int page, List<String> categoriesName) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (categoriesName.getFirst().equals("전체")) {
            return travelItemPostRepository.findAllByOrderByScrapCountDesc(pageable);
        }
        List<Long> categoryIdsByNames = travelItemPostCategoryService.findCategoryIdsByNames(categoriesName);
        return travelItemPostRepository.findByCategoriesOrderByScrapCountDesc(pageable, categoryIdsByNames);
    }

    public Page<TravelItemPost> getPostsByTitleAsc(int page, List<String> categoriesName) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (categoriesName.getFirst().equals("전체")) {
            return travelItemPostRepository.findAllByOrderByTitleAsc(pageable);
        }
        List<Long> categoryIdsByNames = travelItemPostCategoryService.findCategoryIdsByNames(categoriesName);
        return travelItemPostRepository.findByCategoriesOrderByTitleAsc(pageable, categoryIdsByNames);
    }


    /**
     * 새로 생성할 게시글
     * @param post TravelPost 객체
     * @return TravelPost
     */
    public TravelItemPost createPost(TravelItemPost post) {
        // 본문의 이미지 태그에서 이미지 링크 추출 후 저장
        List<String> imageUrls = extractImageUrls(post.getContent());
        for (String url : imageUrls) {
            Image image = imageService.findByUrl(url);
            post.addImageMapping(image);
        }
        return travelItemPostRepository.save(post);
    }

    /**
     * html 형식으로 된 게시글의 본문에서 img 태그의 src 내용을 추출하여 리스트로 반환하는 메소드입니다.
     * 추출된 내용은 DB에 저장된 이미지 url 입니다.
     * @param content 게시글 본문
     * @return 이미지 urls
     */
    private List<String> extractImageUrls(String content) {
        List<String> imageUrls = new ArrayList<>();
        Pattern pattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"[^>]*>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }
        return imageUrls;
    }

    public TravelItemPost findTravelItemPostById(Long id) {
        return travelItemPostRepository.findById(id).orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    }


    /**
     * 게시글 조회수 증가
     * @param post TravelPost
     * @return TravelItemPost
     */
    public TravelItemPost increaseViewCount(TravelItemPost post){
        if(post.getId() == 0) throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        if(!travelItemPostRepository.existsById(post.getId())) throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        post.increaseViewCount();
        return travelItemPostRepository.save(post);
    }

    /**
     * 게시글 좋아요 토글
     * @param post 게시글
     * @param user 해당 유저
     * @return TravelItemPost
     */
    @Transactional
    public TravelItemPost toggleLike(TravelItemPost post, User user){
        if(!likedTravelItemPostRepository.existsByPostAndUser(post, user)){
            // 좋아요를 누른 적이 없는 경우 (좋아요)
            post.addLike(user);
        }
        else{
            // 좋아요를 누른 적이 있는 경우 (좋아요 취소)
            post.removeLike(user);
        }
        // 변경 사항은 트랜잭션 커밋 시 자동으로 저장됩니다.
        return post;
    }

    /**
     * 게시글 스크랩 토글
     * @param post 게시글
     * @param user 해당 유저
     * @return TravelItemPost
     */
    @Transactional
    public TravelItemPost toggleScrap(TravelItemPost post, User user){
        if(!scrapTravelItemPostRepository.existsByPostAndUser(post, user)){
            // 좋아요를 누른 적이 없는 경우 (좋아요)
            post.addScrap(user);
        }
        else{
            // 좋아요를 누른 적이 있는 경우 (좋아요 취소)
            post.removeScrap(user);
        }
        // 변경 사항은 트랜잭션 커밋 시 자동으로 저장됩니다.
        return post;
    }
}
