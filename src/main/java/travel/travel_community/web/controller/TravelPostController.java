package travel.travel_community.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.PostHandler;
import travel.travel_community.converter.postConverter.PostConverter;
import travel.travel_community.converter.RegionConverter;
import travel.travel_community.converter.postConverter.TravelPostConverter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.service.*;
import travel.travel_community.service.system.ServerLogService;
import travel.travel_community.web.dto.postDTO.PostRequestDTO;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostRequestDTO;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostResponseDTO;
import travel.travel_community.web.dto.regionDTO.RegionResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travelPost")
@RequiredArgsConstructor
public class TravelPostController {

    private final UserService userService;
    private final TravelPostService travelPostService;
    private final TravelPostCategoryService travelPostCategoryService;

    //--------------------------- 메인페이지 기능 ---------------------------
    /**
     * 7일 동안 조회수가 가장 많은 순서대로 게시글을 가져옴
     * @return 여행 게시글 리스트 (30개)
     */
    @GetMapping("/topTravelPosts")
    public ApiResponse<PostResponseDTO.TravelPostsResultDTO> getTopTravelPosts() {
        List<TravelPost> posts = travelPostService.getTopTravelPosts();
        return ApiResponse.onSuccess(PostConverter.toTravelPostsResultDTO(posts));
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
    @GetMapping("/allPosts")
    public ApiResponse<TravelPostResponseDTO.ViewAllResultDTO> getAllPosts(@ModelAttribute PostRequestDTO.ViewAllDTO request) {
        String orderBy = request.getOrderBy();
        int page = request.getPage() - 1;

        if(page < 0){
            throw new PostHandler(ErrorStatus.PAGE_OUT_OF_BOUNDS);
        }

        // 정렬 키워드 분석
        Page<TravelPost> posts = switch (orderBy) {
            case "latest" -> travelPostService.getLatestPosts(page);
            case "oldest" -> travelPostService.getOldestPosts(page);
            case "views" -> travelPostService.getMostViewedPosts(page);
            case "likes" -> travelPostService.getMostLikedPosts(page);
            case "name" -> travelPostService.getPostsByTitleAsc(page);
            // 에러 발생 코드
            default -> throw new PostHandler(ErrorStatus.ORDER_BY_VALUE_ERROR);
        };

        if (posts == null) {
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        }
        if (posts.getTotalPages() <= page) {
            throw new PostHandler(ErrorStatus.PAGE_OUT_OF_BOUNDS);
        }

        int minPageIdx = Math.max(0, (page / 5) * 5) + 1;
        int maxPageIdx = Math.min(posts.getTotalPages(), (1 + page / 5) * 5);

        return ApiResponse.onSuccess(
                TravelPostConverter.toViewAllResultDTO(
                        posts, page + 1, orderBy, minPageIdx, maxPageIdx
                )
        );
    }
    //---------------------------------------------------------------------


    @PostMapping("/create")
    public ApiResponse<PostResponseDTO.TravelPostDTO> createPost(@RequestBody @Valid TravelPostRequestDTO.CreatePostDTO request) {
        User author = userService.findUserByUserId(request.getUserid());
        Continent continent = travelPostCategoryService.findContinentById(request.getContinent());
        Country country = travelPostCategoryService.findCountryById(request.getCountry());
        TravelPost post = new TravelPost();
        post.setAuthor(author);
        post.setContinent(continent);
        post.setCountry(country);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post = travelPostService.createPost(post);
        return ApiResponse.onSuccess(PostConverter.toTravelPostResultDTO(post));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponseDTO.TravelPostDTO> getPost(@PathVariable Long id) {
        TravelPost post = travelPostService.findTravelPostById(id);
        return ApiResponse.onSuccess(PostConverter.toTravelPostResultDTO(post));
    }
}
