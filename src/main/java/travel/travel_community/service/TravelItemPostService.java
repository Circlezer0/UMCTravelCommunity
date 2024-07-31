package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.repository.TravelItemPostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelItemPostService {

    private final TravelItemPostRepository travelItemPostRepository;

    public int getAllPostCount(){
        return travelItemPostRepository.findAll().size();
    }
    public List<TravelItemPost> getTopTravelItemPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelItemPostRepository.findRecentTopViewedItemPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }
}
