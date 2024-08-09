package travel.travel_community.web.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CommentResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentDTO{
        private Long commentId;
        private String content;
        private Long postId;
        private Long userId;
        private List<CommentDTO> replies;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentsDTO {
        List<CommentDTO> comments;
    }
}
