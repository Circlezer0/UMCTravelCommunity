package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.CategoryHandler;
import travel.travel_community.entity.posts.categories.TravelItemCategory;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.repository.TravelItemCategoryRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelItemPostCategoryService {

    private final TravelItemCategoryRepository travelItemCategoryRepository;

    public TravelItemCategory findCategoryByName(String name){
        return travelItemCategoryRepository.findTravelItemCategoryByName(name).orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
    }

    private boolean isValidCategory(String categoryName) {
        return travelItemCategoryRepository.existsByName(categoryName);
    }

    public TravelItemCategory addCategory(String name){
        if(travelItemCategoryRepository.existsByName(name)){
            throw new CategoryHandler(ErrorStatus.CATEGORY_ALREADY_EXIST);
        }
        TravelItemCategory travelItemCategory = new TravelItemCategory();
        travelItemCategory.setName(name);
        return travelItemCategoryRepository.save(travelItemCategory);
    }

    public List<TravelItemCategory> findCategoriesByName(List<String> names) {
        List<TravelItemCategory> ret = new ArrayList<>();
        for (String name : names) {
            ret.add(findCategoryByName(name));
        }
        return ret;
    }

    public List<Long> findCategoryIdsByNames(List<String> categoryNames) {
        List<TravelItemCategory> list = travelItemCategoryRepository.findByNameIn(categoryNames).stream().toList();
        List<Long> ret = new ArrayList<>();
        for(TravelItemCategory category:list){
            ret.add(category.getId());
        }
        return ret;
    }

    public void initializeCategories() {
        String[] categories = {
                "공항", "의류·신발", "배선·쇼핑", "가방·케리어", "라이프·뷰티", "유아",
                "티켓", "여권·비자", "의료·안전", "웰·앱", "스포츠·레저", "기타"
        };

        for (String categoryName : categories) {
            if (!isValidCategory(categoryName)) {
                addCategory(categoryName);
            }
        }
    }


}
