package travel.travel_community.web.dto.postDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResultDTO{
        private int likeCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapResultDTO{
        private int scrapCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewResultDTO{
        private int viewCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResultDTO{
        private Long cmId; //댓글 번호 (pk)
        private String cmContent; //댓글내용
        private LocalDateTime cmDate; //댓글 작성시간
        private Long postNo; //댓글 달린 게시판 번호
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReCommentResultDTO{
        private Long reCmId; // 대댓글 번호(pk)
        private String reCmContent; //대댓글 내용
        private LocalDateTime reCmDate; // 대댓글 작성 시간
        private Long cmId; // 대댓글 달린 댓글 번호
    }

}
