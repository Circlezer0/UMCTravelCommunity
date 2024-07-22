package travel.travel_community.converter;

import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.web.dto.regionDTO.RegionResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RegionConverter {

    public static RegionResponseDTO.CountryDTO toCountryDTO(Country country) {
        return RegionResponseDTO.CountryDTO.builder()
                .id(country.getId())
                .countryName(country.getName())
                .build();
    }

    public static RegionResponseDTO.TopCountriesResultDTO toTopCountriesResultDTO(List<Country> countries){
        List<RegionResponseDTO.CountryDTO> collect = countries.stream()
                .map(RegionConverter::toCountryDTO)
                .collect(Collectors.toList());
        return RegionResponseDTO.TopCountriesResultDTO.builder()
                .topCountries(collect)
                .build();
    }
}
