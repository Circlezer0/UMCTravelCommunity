package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travel_community.repository.TravelItemCategoryRepository;

@Service
@RequiredArgsConstructor
public class TravelItemPostCategoryService {

    private final TravelItemCategoryRepository travelItemCategoryRepository;

}
