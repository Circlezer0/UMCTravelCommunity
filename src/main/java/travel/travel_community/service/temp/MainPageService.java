package travel.travel_community.service.temp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.repository.CountryRepository;
import travel.travel_community.repository.TravelItemPostRepository;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final TravelPostRepository travelPostRepository;
    private final TravelItemPostRepository travelItemPostRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    public List<TravelPost> getTopTravelPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelPostRepository.findRecentTopViewedPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }

    public List<TravelItemPost> getTopTravelItemPosts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return travelItemPostRepository.findRecentTopViewedItemPosts(sevenDaysAgo, PageRequest.of(0, 30));
    }

    public List<Country> getTopCountries() {
        List<Object[]> results = countryRepository.findTopCountriesByPostCount(PageRequest.of(0, 8));
        return results.stream()
                .map(result -> (Country) result[0])
                .collect(Collectors.toList());
    }

    public List<User> getRandomUsers() {
        return userRepository.findRandomUsers(PageRequest.of(0, 18));
    }
}
