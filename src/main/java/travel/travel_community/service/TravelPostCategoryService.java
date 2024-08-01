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

    public Country findCountryById(Long id){
        Optional<Country> ret = countryRepository.findById(id);
        if(ret.isEmpty()){
            throw new RegionHandler(ErrorStatus.COUNTRY_NOT_FOUND);
        }
        return ret.get();
    }

    public Country findCountryByName(String name){
        Optional<Country> ret = countryRepository.findCountryByName(name);
        if(ret.isEmpty()){
            throw new RegionHandler(ErrorStatus.COUNTRY_NOT_FOUND);
        }
        return ret.get();
    }

    public Continent findContinentById(Long id){
        Optional<Continent> ret = continentRepository.findById(id);
        if(ret.isEmpty()){
            throw new RegionHandler(ErrorStatus.CONTINENT_NOT_FOUND);
        }
        return ret.get();
    }

    public Continent findContinentByName(String name){
        Optional<Continent> ret = continentRepository.findCountryByName(name);
        if(ret.isEmpty()){
            throw new RegionHandler(ErrorStatus.COUNTRY_NOT_FOUND);
        }
        return ret.get();
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
}
