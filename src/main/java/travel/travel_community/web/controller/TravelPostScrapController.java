package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.PostConverter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.service.TravelPostService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class TravelPostScrapController {
    private final TravelPostService postService;
    private final UserService userService;

    @PostMapping("/{id}/scrappes")
    public ApiResponse<PostResponseDTO.ScrapResultDTO> incrementScrapCount(@PathVariable("id") Long postId, String email){
        User user = userService.findUserId(email);
        postService.addScrap(postId,user);
        TravelPost post = postService.save(postId);
        return ApiResponse.onSuccess(PostConverter.toScrapResultDTO(post));
    }


}
