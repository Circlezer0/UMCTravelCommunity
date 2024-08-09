package travel.travel_community.web.dto.commentDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import travel.travel_community.validation.annotation.ExistUser;

public class CommentRequestDTO {

    @Getter
    public static class CommentCreateDTO{
        @ExistUser
        private String userid;
        private Long postId;
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        @Size(max = 1000, message = "댓글은 1000자를 초과할 수 없습니다.")
        private String content;
        private Long parentCommentId;
    }

    @Getter
    @Setter
    public static class ViewCommentsDTO{
        // 예외처리 어노테이션 만들것
        private Long postId;
    }
}
