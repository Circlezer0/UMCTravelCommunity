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
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.repository.mapping.LikedTravelPostRepository;
import travel.travel_community.repository.mapping.ScrapTravelPostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TravelPostService {

    private final TravelPostRepository travelPostRepository;
    private final TravelPostCategoryService travelPostCategoryService;
    private final LikedTravelPostRepository likedTravelPostRepository;
    private final ScrapTravelPostRepository scrapTravelPostRepository;
    private final ImageService imageService;

    private static final int PAGE_SIZE = 16; // 한 페이지에 보여줄 게시글 수

    public int getAllPostCount(){
        return travelPostRepository.findAll().size();
    }

    public TravelPost findTravelPostById(Long id) {
        return travelPostRepository.findById(id)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    }

    /**
     * 게시글 업데이트. ID가 0이면 예외 발생
     * @param post 업데이트할 TravelPost 객체
     * @return TravelPost
     */
    public TravelPost updatePost(TravelPost post){
        if(post.getId() == 0) throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        return travelPostRepository.save(post);
    }

    /**
     * 새로 생성할 게시글
     * @param post TravelPost 객체
     * @return TravelPost
     */
    public TravelPost createPost(TravelPost post) {
        // 본문의 이미지 태그에서 이미지 링크 추출 후 저장
        List<String> imageUrls = extractImageUrls(post.getContent());
        for (String url : imageUrls) {
            Image image = imageService.findByUrl(url);
            post.addImageMapping(image);
        }
        return travelPostRepository.save(post);
    }

    /**
     * 게시글 조회수 증가
     * @param post TravelPost
     * @return TravelPost
     */
    public TravelPost increaseViewCount(TravelPost post){
        if(post.getId() == 0) throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        if(!travelPostRepository.existsById(post.getId())) throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        post.increaseViewCount();
        return travelPostRepository.save(post);
    }

    /**
     * 게시글 좋아요 토글
     * @param post 게시글
     * @param user 해당 유저
     * @return TravelPost
     */
    @Transactional
    public TravelPost toggleLike(TravelPost post, User user){
        if(!likedTravelPostRepository.existsByPostAndUser(post, user)){
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
     * @return TravelPost
     */
    @Transactional
    public TravelPost toggleScrap(TravelPost post, User user){
        if(!scrapTravelPostRepository.existsByPostAndUser(post, user)){
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

    /**
     * 최근 7일간 조회수가 높은 게시글 30개
     * @return TravelPost 리스트
     */
    public List<TravelPost> getTopTravelPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelPostRepository.findRecentTopViewedPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }



    /**
     * 게시글을 최신순으로 조회
     * @param page 페이지 번호 (0부터 시작)
     * @return TravelPost 리스트
     */
    public Page<TravelPost> getLatestPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByCreatedDateDesc(pageable);
    }
    public Page<TravelPost> getLatestPosts(int page, String continentName) {
        if (continentName.equals("전체")) return getLatestPosts(page);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentOrderByCreatedDateDesc(pageable, continent);
    }
    public Page<TravelPost> getLatestPosts(int page, String continentName, String countryName) {
        if (countryName.equals("전체")) return getLatestPosts(page, continentName);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Country country = travelPostCategoryService.findCountryByName(countryName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentAndCountryOrderByCreatedDateDesc(pageable, continent, country);
    }

    /**
     * 게시글을 오래된 순으로 조회
     * @param page 페이지 번호 (0부터 시작)
     * @return TravelPost 리스트
     */
    public Page<TravelPost> getOldestPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByCreatedDateAsc(pageable);
    }
    public Page<TravelPost> getOldestPosts(int page, String continentName) {
        if (continentName.equals("전체")) return getLatestPosts(page);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentOrderByCreatedDateAsc(pageable, continent);
    }
    public Page<TravelPost> getOldestPosts(int page, String continentName, String countryName) {
        if (countryName.equals("전체")) return getLatestPosts(page, continentName);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Country country = travelPostCategoryService.findCountryByName(countryName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentAndCountryOrderByCreatedDateAsc(pageable, continent, country);
    }

    /**
     * 게시글을 이름 순으로 조회 (오름차순)
     * @param page 페이지 번호 (0부터 시작)
     * @return TravelPost 리스트
     */
    public Page<TravelPost> getPostsByTitleAsc(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByTitleAsc(pageable);
    }
    public Page<TravelPost> getPostsByTitleAsc(int page, String continentName) {
        if (continentName.equals("전체")) return getLatestPosts(page);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentOrderByTitleAsc(pageable, continent);
    }
    public Page<TravelPost> getPostsByTitleAsc(int page, String continentName, String countryName) {
        if (countryName.equals("전체")) return getLatestPosts(page, continentName);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Country country = travelPostCategoryService.findCountryByName(countryName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentAndCountryOrderByTitleAsc(pageable, continent, country);
    }

    /**
     * 게시글을 좋아요순으로 조회
     * @param page 페이지 번호 (0부터 시작)
     * @return TravelPost 리스트
     */
    public Page<TravelPost> getMostLikedPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByLikeCountDesc(pageable);
    }
    public Page<TravelPost> getMostLikedPosts(int page, String continentName) {
        if (continentName.equals("전체")) return getLatestPosts(page);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentOrderByLikeCountDesc(pageable, continent);
    }
    public Page<TravelPost> getMostLikedPosts(int page, String continentName, String countryName) {
        if (countryName.equals("전체")) return getLatestPosts(page, continentName);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Country country = travelPostCategoryService.findCountryByName(countryName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentAndCountryOrderByLikeCountDesc(pageable, continent, country);
    }

    /**
     * 게시글을 조회수순으로 조회
     * @param page 페이지 번호 (0부터 시작)
     * @return TravelPost 리스트
     */
    public Page<TravelPost> getMostViewedPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByViewCountDesc(pageable);
    }
    public Page<TravelPost> getMostViewedPosts(int page, String continentName) {
        if (continentName.equals("전체")) return getLatestPosts(page);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentOrderByViewCountDesc(pageable, continent);
    }
    public Page<TravelPost> getMostViewedPosts(int page, String continentName, String countryName) {
        if (countryName.equals("전체")) return getLatestPosts(page, continentName);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Country country = travelPostCategoryService.findCountryByName(countryName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentAndCountryOrderByViewCountDesc(pageable, continent, country);
    }

    /**
     * 게시글을 스크랩수 순으로 조회
     * @param page 페이지 번호 (0부터 시작)
     * @return TravelPost 리스트
     */
    public Page<TravelPost> getMostScrapedPosts(int page){
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByScrapCountDesc(pageable);
    }
    public Page<TravelPost> getMostScrapedPosts(int page, String continentName) {
        if (continentName.equals("전체")) return getLatestPosts(page);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentOrderByScrapCountDesc(pageable, continent);
    }
    public Page<TravelPost> getMostScrapedPosts(int page, String continentName, String countryName) {
        if (countryName.equals("전체")) return getLatestPosts(page, continentName);
        Continent continent = travelPostCategoryService.findContinentByName(continentName);
        Country country = travelPostCategoryService.findCountryByName(countryName);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByContinentAndCountryOrderByScrapCountDesc(pageable, continent, country);
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
}
