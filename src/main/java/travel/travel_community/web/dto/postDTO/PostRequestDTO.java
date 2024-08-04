package travel.travel_community.web.dto.postDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelPost;

public class PostRequestDTO {

    @Getter
    public static class LikeRequestDTO{
        @NotBlank(message = "게시글 번호를 입력해야 합니다.")
        private int postId;
        @NotBlank(message = "유저 번호를 입력해야 합니다.")
        private int userId;
    }

    @Getter
    public static class ScrapRequestDTO{
        @NotBlank(message = "게시글 번호를 입력해야 합니다.")
        private int postId;
        @NotBlank(message = "유저 번호를 입력해야 합니다.")
        private int userId;
    }

    @Getter
    public static class ViewRequestDTO{
        @NotBlank(message = "게시글 번호를 입력해야 합니다.")
        private int postId;
    }

    @Getter
    public static class CommentRequestDTO{
        @NotBlank(message = "게시글 번호를 입력해야 합니다.")
        private int postId;
        @NotBlank(message = "유저 번호를 입력해야 합니다.")
        private int userId;
    }

    @Getter
    public static class ReCommentRequestDTO{
        @NotBlank(message = "게시글 번호를 입력해야 합니다.")
        private int postId;
        @NotBlank(message = "유저 번호를 입력해야 합니다.")
        private int userId;
        @NotBlank(message = "댓글 번호를 입력해야 합니다.")
        private int commentId;
    }

}
