package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.repository.CountryRepository;
import travel.travel_community.repository.TravelItemPostRepository;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPostService {

    private final TravelPostRepository travelPostRepository;
    private static final int PAGE_SIZE = 16; // 한 페이지에 보여줄 게시글 수

    public int getAllPostCount(){
        return travelPostRepository.findAll().size();
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
}
