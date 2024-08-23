package travel.travel_community.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.PostHandler;
import travel.travel_community.converter.postConverter.PostConverter;
import travel.travel_community.converter.postConverter.TravelItemPostConverter;
import travel.travel_community.converter.postConverter.TravelPostConverter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.categories.TravelItemCategory;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.service.*;
import travel.travel_community.web.dto.postDTO.PostRequestDTO;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.postDTO.travelItemPostDTO.TravelItemPostRequestDTO;
import travel.travel_community.web.dto.postDTO.travelItemPostDTO.TravelItemPostResponseDTO;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostRequestDTO;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostResponseDTO;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/travelItemPost")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "여행 아이템 게시글", description = "여행 아이템 게시글 관련 API")
public class TravelItemPostController {

    private final UserService userService;
    private final TravelItemPostService travelItemPostService;
    private final TravelItemPostCategoryService travelItemPostCategoryService;


    //--------------------------- 메인페이지 기능 ---------------------------
    /**
     * 7일동안 조회수가 가장 많은 순서대로 여행가방 게시글을 가져옴
     * @return 여행가방 게시글 리스트 (30개)
     */
    @GetMapping("/topTravelItemPosts")
    @Operation(summary = "메인 페이지 게시글 조회", description = "페이지네이션과 정렬 옵션을 사용하여 메인페이지에서 보여줄 여행 아이템 게시글을 조회합니다.")
    public ApiResponse<PostResponseDTO.TravelItemPostsResultDTO> getTopTravelItemPosts() {
        List<TravelItemPost> posts = travelItemPostService.getTopTravelItemPosts();
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostsResultDTO(posts));
    }
    //---------------------------------------------------------------------


    //------------------------- 게시글 조회 ---------------------------------
    @GetMapping("/allPosts")
    @Operation(summary = "모든 게시글 조회", description = "페이지네이션과 정렬 옵션, 카테고리 옵션을 사용하여 모든 여행 아이템 게시글을 조회합니다.")
    public ApiResponse<TravelItemPostResponseDTO.ViewAllResultDTO> getAllPosts(@ModelAttribute @Valid TravelItemPostRequestDTO.ViewAllDTO request) {
        String orderBy = request.getOrderBy();
        int page = request.getPage() - 1;
        List<String> categories = Arrays.stream(request.getCategories().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());


        if(page < 0){
            throw new PostHandler(ErrorStatus.PAGE_OUT_OF_BOUNDS);
        }

        // 정렬 키워드 분석
        Page<TravelItemPost> posts = switch (orderBy) {
            case "latest" -> travelItemPostService.getLatestPosts(page, categories);
            case "oldest" -> travelItemPostService.getOldestPosts(page, categories);
            case "views" -> travelItemPostService.getMostViewedPosts(page, categories);
            case "likes" -> travelItemPostService.getMostLikedPosts(page, categories);
            case "scrap" -> travelItemPostService.getMostScrapedPosts(page, categories);
            case "name" -> travelItemPostService.getPostsByTitleAsc(page, categories);
            // 에러 발생 코드
            default -> throw new PostHandler(ErrorStatus.ORDER_BY_VALUE_ERROR);
        };

        if (posts == null) {
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        }
        if(posts.stream().findAny().isEmpty()){
            return ApiResponse.onSuccess(
                    TravelItemPostConverter.toViewAllResultDTO(
                            posts, page + 1, orderBy, 1, 1
                    )
            );
        }
        if (posts.getTotalPages() <= page) {
            throw new PostHandler(ErrorStatus.PAGE_OUT_OF_BOUNDS);
        }

        int minPageIdx = Math.max(0, (page / 5) * 5) + 1;
        int maxPageIdx = Math.min(posts.getTotalPages(), (1 + page / 5) * 5);

        return ApiResponse.onSuccess(
                TravelItemPostConverter.toViewAllResultDTO(
                        posts, page + 1, orderBy, minPageIdx, maxPageIdx
                )
        );
    }
    //---------------------------------------------------------------------


    @PostMapping("/create")
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    public ApiResponse<PostResponseDTO.TravelItemPostDTO> createPost(@RequestBody @Valid TravelItemPostRequestDTO.CreatePostDTO request) {
        User author = userService.findUserByUserId(request.getUserid());
        List<TravelItemCategory> categories = travelItemPostCategoryService.findCategoriesByName(request.getCategories());
        TravelItemPost post = new TravelItemPost();
        post.setAuthor(author);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        for(TravelItemCategory category:categories){
            post.addCategory(category);
        }
        post = travelItemPostService.createPost(post);
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostResultDTO(post));
    }

    @GetMapping("/{id}")
    @Operation(summary = "게시글 조회", description = "PathParameter의 아이디에 해당하는 게시글을 조회합니다.")
    public ApiResponse<PostResponseDTO.TravelItemPostDTO> getPost(@PathVariable Long id) {
        TravelItemPost post = travelItemPostService.findTravelItemPostById(id);
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostResultDTO(post));
    }

    @GetMapping("/{id}/viewCount/increase")
    @Operation(summary = "게시글 조회수 증가", description = "아이디에 해당하는 게시글의 조회수를 증가시킵니다.")
    public ApiResponse<PostResponseDTO.TravelItemPostDTO> incrementViewCount(@PathVariable Long id){
        TravelItemPost post = travelItemPostService.findTravelItemPostById(id);
        post = travelItemPostService.increaseViewCount(post);
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostResultDTO(post));    }

    @GetMapping("/{id}/like/toggle")
    @Operation(summary = "게시글 좋아요 토글", description = "유저 아이디와 게시글 아이디에 해당하는 게시글의 좋아요를 토글시킵니다.")
    public ApiResponse<PostResponseDTO.TravelItemPostDTO> toggleLike(
            @PathVariable Long id, @ModelAttribute @Valid UserRequestDTO.UserIdDTO request){
        String userid = request.getUserid();
        User user = userService.findUserByUserId(userid);
        TravelItemPost post = travelItemPostService.findTravelItemPostById(id);
        post = travelItemPostService.toggleLike(post, user);
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostResultDTO(post));    }

    @GetMapping("/{id}/scrap/toggle")
    @Operation(summary = "게시글 스크랩 토글", description = "유저 아이디와 게시글 아이디에 해당하는 게시글의 스크랩을 토글시킵니다.")
    public ApiResponse<PostResponseDTO.TravelItemPostDTO> toggleScrap(
            @PathVariable Long id, @ModelAttribute @Valid UserRequestDTO.UserIdDTO request){
        String userid = request.getUserid();
        User user = userService.findUserByUserId(userid);
        TravelItemPost post = travelItemPostService.findTravelItemPostById(id);
        post = travelItemPostService.toggleScrap(post, user);
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostResultDTO(post));    }
}
