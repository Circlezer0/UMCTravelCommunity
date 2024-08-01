package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.PostHandler;
import travel.travel_community.converter.postConverter.TravelPostConverter;
import travel.travel_community.entity.Image;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostRequestDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TravelPostService {

    private final TravelPostRepository travelPostRepository;
    private final TravelPostCategoryService travelPostCategoryService;
    private final ImageService imageService;

    private static final int PAGE_SIZE = 16; // 한 페이지에 보여줄 게시글 수

    public int getAllPostCount(){
        return travelPostRepository.findAll().size();
    }

    public TravelPost findTravelPostById(Long id) {
        return travelPostRepository.findById(id)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    }

    // 게시글 저장
    public TravelPost createPost(TravelPost post) {
        // 본문의 이미지 태그에서 이미지 링크 추출 후 저장
        List<String> imageUrls = extractImageUrls(post.getContent());
        for (String url : imageUrls) {
            Image image = imageService.findByUrl(url);
            post.addImageMapping(image);
        }
        return travelPostRepository.save(post);
    }

    public List<TravelPost> getTopTravelPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelPostRepository.findRecentTopViewedPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }

    // 게시글 최신순으로 조회
    public Page<TravelPost> getLatestPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    // 게시글 오래된순으로 조회
    public Page<TravelPost> getOldestPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByCreatedDateAsc(pageable);
    }

    // 게시글 이름순으로 조회
    public Page<TravelPost> getPostsByTitleAsc(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByTitleAsc(pageable);
    }

    // 게시글 좋아요순으로 조회
    public Page<TravelPost> getMostLikedPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByLikeCountDesc(pageable);
    }

    // 게시글 조회순으로 조회
    public Page<TravelPost> getMostViewedPosts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return travelPostRepository.findAllByOrderByViewCountDesc(pageable);
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
