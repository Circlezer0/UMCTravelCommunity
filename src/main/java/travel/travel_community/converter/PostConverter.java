package travel.travel_community.converter;

import travel.travel_community.entity.posts.Comment;
import travel.travel_community.entity.posts.ReComment;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

public class PostConverter {
    //좋아요 수 반환해주는 response
    public static PostResponseDTO.LikeResultDTO toLikeResultDTO(TravelPost travelPost){
        return PostResponseDTO.LikeResultDTO.builder()
                .likeCount(travelPost.getLikeCount())
                .build();
    }

    //스크랩 수 반환해주는 response
    public static PostResponseDTO.ScrapResultDTO toScrapResultDTO(TravelPost travelPost){
        return PostResponseDTO.ScrapResultDTO.builder()
                .scrapCount(travelPost.getScrapCount())
                .build();
    }

    //조회수 반환해주는 response
    public static PostResponseDTO.ViewResultDTO toViewResultDTO(TravelPost travelPost){
        return PostResponseDTO.ViewResultDTO.builder()
                .viewCount(travelPost.getViewCount())
                .build();
    }

    //댓글 response
    public static PostResponseDTO.CommentResultDTO toCommentResultDTO(Comment comment) {
        return PostResponseDTO.CommentResultDTO.builder()
                .cmId(comment.getId())
                .postNo(comment.getTravelPost().getId())
                .cmContent(comment.getContent())
                .cmDate(comment.getUpdatedDate())
                .build();
    }

    //대댓글 response
    public static PostResponseDTO.ReCommentResultDTO toReCommentResultDTO(ReComment reply) {
        return PostResponseDTO.ReCommentResultDTO.builder()
                .reCmId(reply.getId())
                .reCmContent(reply.getContent())
                .cmId(reply.getParentComment().getId())
                .reCmDate(reply.getUpdatedDate())
                .build();
    }

}
