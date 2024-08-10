package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.RegionHandler;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.repository.ContinentRepository;
import travel.travel_community.repository.CountryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPostCategoryService {
    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;

    public Continent addContinent(String name){
        if(continentRepository.existsByName(name)){
            throw new RuntimeException();
        }
        Continent continent = new Continent();
        continent.setName(name);
        return continentRepository.save(continent);
    }
    public Country addCountry(Continent continent, String name){
        if(countryRepository.existsByName(name)){
            throw new RuntimeException();
        }
        Country country = new Country();
        country.setName(name);
        country.setContinent(continent);
        return countryRepository.save(country);
    }

    public Country findCountryById(Long id){
        return countryRepository.findById(id).orElseThrow(() -> new RegionHandler(ErrorStatus.COUNTRY_NOT_FOUND));
    }

    public Country findCountryByName(String name){
        return countryRepository.findCountryByName(name).orElseThrow(() -> new RegionHandler(ErrorStatus.COUNTRY_NOT_FOUND));
    }
    public Country findCountryByNameAndContinent(String name, Continent continent){
        return countryRepository.findCountryByNameAndContinent(name, continent).orElseThrow(() -> new RegionHandler(ErrorStatus.COUNTRY_NOT_FOUND));
    }

    public Continent findContinentById(Long id){
        return continentRepository.findById(id).orElseThrow(() -> new RegionHandler(ErrorStatus.CONTINENT_NOT_FOUND));
    }

    public Continent findContinentByName(String name){
        return continentRepository.findCountryByName(name).orElseThrow(() -> new RegionHandler(ErrorStatus.CONTINENT_NOT_FOUND));
    }

    public List<Country> getTopCountries() {
        List<Object[]> results = countryRepository.findTopCountriesByPostCount(PageRequest.of(0, 8));
        return results.stream()
                .map(result -> (Country) result[0])
                .collect(Collectors.toList());
    }

    public boolean isValidContinent(Long id){
        return continentRepository.existsById(id);
    }
    public boolean isValidCountry(Long id){
        return countryRepository.existsById(id);
    }
    public boolean isValidContinent(String name){
        return continentRepository.existsByName(name);
    }
    public boolean isValidCountry(String name){
        return countryRepository.existsByName(name);
    }


    /**
     * 지역 초기 세팅을 위한 코드
     */
    public void initializeRegions() {
        String[][] regions = {
                {"대한민국", "서울·경기도", "강원도", "경상북도", "경상남도", "전라북도", "전라남도", "제주도", "충청북도", "충청남도"},
                {"남아메리카", "브라질", "볼리비아", "아르헨티나", "칠레", "페루", "기타"},
                {"북아메리카", "미국·동부", "미국·서부", "멕시코", "캐나다", "기타"},
                {"아시아", "베트남", "일본", "중국", "필리핀", "기타"},
                {"아프리카", "모로코", "이집트", "기타"},
                {"유럽", "동유럽", "북유럽", "서유럽", "기타"},
                {"중동", "사우디", "터키", "기타"},
                {"호주", "호주", "하와이", "기타"}
        };

        for (String[] region : regions) {
            String continentName = region[0];
            Continent continent;
            if (!isValidContinent(continentName)) {
                continent = addContinent(continentName);
            } else {
                continent = findContinentByName(continentName);
            }

            for (int i = 1; i < region.length; i++) {
                String countryName = region[i];
                if (!isValidCountry(countryName)) {
                    addCountry(continent, countryName);
                }
            }
        }
    }
}
