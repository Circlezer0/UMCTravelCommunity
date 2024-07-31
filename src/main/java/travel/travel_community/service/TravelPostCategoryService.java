package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.repository.ContinentRepository;
import travel.travel_community.repository.CountryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPostCategoryService {
    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;

    public List<Country> getTopCountries() {
        List<Object[]> results = countryRepository.findTopCountriesByPostCount(PageRequest.of(0, 8));
        return results.stream()
                .map(result -> (Country) result[0])
                .collect(Collectors.toList());
    }
}
