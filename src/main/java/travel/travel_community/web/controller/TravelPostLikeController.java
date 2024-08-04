package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.PostConverter;
import travel.travel_community.converter.UserConverter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.LikedPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.repository.LikedPostRepository;
import travel.travel_community.service.TravelPostService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class TravelPostLikeController {
    private final TravelPostService postService;
    private final UserService userService;
    private final LikedPostRepository likedPostRepository;


    @PostMapping("/{id}/likes")
    public ApiResponse<PostResponseDTO.LikeResultDTO> incrementLikeCount(@PathVariable("id") Long postId,  String email){
        User user = userService.findUserId(email);
        postService.addLike(postId,user);
        TravelPost post = postService.save(postId);
        return ApiResponse.onSuccess(PostConverter.toLikeResultDTO(post));
    }
}
