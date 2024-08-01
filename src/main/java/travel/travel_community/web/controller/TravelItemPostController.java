package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.postConverter.PostConverter;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.service.*;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travelItemPost")
@RequiredArgsConstructor
public class TravelItemPostController {

    private final TravelItemPostService travelItemPostService;


    //--------------------------- 메인페이지 기능 ---------------------------
    /**
     * 7일동안 조회수가 가장 많은 순서대로 여행가방 게시글을 가져옴
     * @return 여행가방 게시글 리스트 (30개)
     */
    @GetMapping("/topTravelItemPosts")
    public ApiResponse<PostResponseDTO.TravelItemPostsResultDTO> getTopTravelItemPosts() {
        List<TravelItemPost> posts = travelItemPostService.getTopTravelItemPosts();
        return ApiResponse.onSuccess(PostConverter.toTravelItemPostsResultDTO(posts));
    }
    //---------------------------------------------------------------------

}
