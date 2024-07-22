package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.PostConverter;
import travel.travel_community.converter.RegionConverter;
import travel.travel_community.converter.UserConverter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.service.temp.MainPageService;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.regionDTO.RegionResponseDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping("/topTravelPosts")
    public ApiResponse<PostResponseDTO.GetTopTravelPostsResultDTO> getTopTravelPosts() {
        List<TravelPost> posts = mainPageService.getTopTravelPosts();
        return ApiResponse.onSuccess(PostConverter.toTopTravelPostsResultDTO(posts));
    }

    @GetMapping("/topTravelItemPosts")
    public ApiResponse<PostResponseDTO.GetTopTravelItemResultPosts> getTopTravelItemPosts() {
        List<TravelItemPost> posts = mainPageService.getTopTravelItemPosts();
        return ApiResponse.onSuccess(PostConverter.toTopTravelItemPostsResultDTO(posts));
    }

    @GetMapping("/topCountries")
    public ApiResponse<RegionResponseDTO.TopCountriesResultDTO> getTopCountries() {
        List<Country> countries = mainPageService.getTopCountries();
        return ApiResponse.onSuccess(RegionConverter.toTopCountriesResultDTO(countries));
    }

    @GetMapping("/topUsers")
    public ApiResponse<UserResponseDTO.TopUsersResultDTO> getTopUsers() {
        // 랜덤으로 유저 30명 가져오는걸 나중에 좋아요를 받은 순서대로 정렬해서 유저를 가져와야 함
        List<User> users = mainPageService.getRandomUsers();
        return ApiResponse.onSuccess(UserConverter.toTopUserResultDTO(users));
    }
    
}