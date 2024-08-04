package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.PostConverter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.service.TravelPostService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.postDTO.PostRequestDTO;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class TravelPostController {
    private final UserService userService;
    private final TravelPostService postService;

    @GetMapping("/{id}")
    public ApiResponse<PostResponseDTO.ViewResultDTO> incrementViewCount(@PathVariable("id") Long postId, String email){
        User user = userService.findUserId(email);
        postService.addView(postId,user);
        TravelPost post = postService.save(postId);
        return ApiResponse.onSuccess(PostConverter.toViewResultDTO(post));
    }
}
