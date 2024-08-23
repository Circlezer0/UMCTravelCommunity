package travel.travel_community.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.commentConverter.TravelCommentConverter;
import travel.travel_community.entity.posts.TravelComment;
import travel.travel_community.service.comment.TravelCommentService;
import travel.travel_community.web.dto.commentDTO.CommentRequestDTO;
import travel.travel_community.web.dto.commentDTO.CommentResponseDTO;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
//@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {

    private final TravelCommentService travelCommentService;

    /**
     * 댓글 작성
     * @param request 유저아이디, 게시글아이디, 내용, 부모댓글아이디(null 가능)
     * @return 작성된 댓글
     */
    @PostMapping
    public ApiResponse<CommentResponseDTO.CommentDTO> addComment(@RequestBody @Valid CommentRequestDTO.CommentCreateDTO request) {
        TravelComment comment = travelCommentService.addComment(
                request.getPostId(), request.getUserid(), request.getContent(), request.getParentCommentId()
        );

        return ApiResponse.onSuccess(TravelCommentConverter.toCommentDTO(comment));
    }

    /**
     * 댓글 삭제. 자식도 함께 삭제.
     * rest 스럽지 않은 api 이지만 원활한 프론트와의 통신을 위해 그냥 이렇게 구현했습니다.
     * @param commentId 삭제할 댓글 아이디
     * @param request
     * @return
     */
    @GetMapping("/{commentId}/delete")
    public ApiResponse<Object> deleteComment(
            @PathVariable Long commentId,
            @ModelAttribute @Valid UserRequestDTO.UserIdDTO request) {
        String userid = request.getUserid();
        travelCommentService.deleteComment(commentId, userid);
        return ApiResponse.onSuccess(null);
    }

    /**
     * 게시글에 대한 댓글 리스트
     * @param request 게시글 id
     * @return 댓글 리스트
     */
    @GetMapping("/comments")
    public ApiResponse<CommentResponseDTO.CommentsDTO> viewComments(@ModelAttribute @Valid CommentRequestDTO.ViewCommentsDTO request){
        List<TravelComment> rootComments = travelCommentService.getRootComments(request.getPostId());
        return ApiResponse.onSuccess(TravelCommentConverter.toCommentsDTO(rootComments));
    }
}
