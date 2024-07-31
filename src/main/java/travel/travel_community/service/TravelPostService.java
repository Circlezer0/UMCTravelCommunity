package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    public int getAllPostCount(){
        return travelPostRepository.findAll().size();
    }

    public List<TravelPost> getTopTravelPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelPostRepository.findRecentTopViewedPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }
}
