package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.PostConverter;
import travel.travel_community.converter.RegionConverter;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.service.*;
import travel.travel_community.service.system.ServerLogService;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.regionDTO.RegionResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travelPost")
@RequiredArgsConstructor
public class TravelPostController {

    private final UserService userService;
    private final TravelPostService travelPostService;
    private final TravelPostCategoryService travelPostCategoryService;
    private final TravelItemPostService travelItemPostService;
    private final TravelItemPostCategoryService travelItemPostCategoryService;

    private final ServerLogService serverLogService;

    //--------------------------- 메인페이지 기능 ---------------------------
    /**
     * 7일 동안 조회수가 가장 많은 순서대로 게시글을 가져옴
     * @return 여행 게시글 리스트 (30개)
     */
    @GetMapping("/topTravelPosts")
    public ApiResponse<PostResponseDTO.GetTopTravelPostsResultDTO> getTopTravelPosts() {
        List<TravelPost> posts = travelPostService.getTopTravelPosts();
        return ApiResponse.onSuccess(PostConverter.toTopTravelPostsResultDTO(posts));
    }

    /**
     * 게시글이 많은 국가 순서대로 8개의 국가를 가져옴
     * @return 국가 리스트 (8개)
     */
    @GetMapping("/topCountries")
    public ApiResponse<RegionResponseDTO.TopCountriesResultDTO> getTopCountries() {
        List<Country> countries = travelPostCategoryService.getTopCountries();
        return ApiResponse.onSuccess(RegionConverter.toTopCountriesResultDTO(countries));
    }
    //---------------------------------------------------------------------


    //------------------------- 게시글 조회 ---------------------------------

    //---------------------------------------------------------------------
}
