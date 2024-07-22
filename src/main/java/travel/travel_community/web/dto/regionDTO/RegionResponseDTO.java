package travel.travel_community.web.dto.regionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.posts.regions.Country;

import java.util.List;

public class RegionResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountryDTO {
        Long id;
        String countryName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCountriesResultDTO {
        List<CountryDTO> topCountries;
    }
}
