package travel.travel_community.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.PostConverter;
import travel.travel_community.entity.posts.Comment;
import travel.travel_community.entity.posts.ReComment;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.service.CommentService;
import travel.travel_community.service.TravelPostService;

import travel.travel_community.web.dto.postDTO.PostResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final TravelPostService postService;

    @PostMapping("/{postId}/write")
    public ApiResponse<PostResponseDTO.CommentResultDTO> commentWrite(@PathVariable("postId") Long postId, @RequestParam("userId") Long userId, @RequestParam("content") String content) {
        Comment comment = commentService.createComment(postId, userId, content);
        return ApiResponse.onSuccess(PostConverter.toCommentResultDTO(comment));
    }

    @PostMapping("/{cmId}/rewrite")
    public ApiResponse<PostResponseDTO.ReCommentResultDTO> reCommentWrite(@PathVariable("cmId") Long cmId, @RequestParam("userId") Long userId, @RequestParam("content") String content) {
        ReComment reply = commentService.createReply(userId, cmId, content);
        return ApiResponse.onSuccess(PostConverter.toReCommentResultDTO(reply));
    }



}
