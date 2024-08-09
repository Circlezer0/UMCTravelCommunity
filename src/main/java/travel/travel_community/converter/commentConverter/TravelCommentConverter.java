package travel.travel_community.converter.commentConverter;

import travel.travel_community.entity.posts.TravelComment;
import travel.travel_community.web.dto.commentDTO.CommentResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TravelCommentConverter {

    public static CommentResponseDTO.CommentDTO toCommentDTO(TravelComment comment) {
        return CommentResponseDTO.CommentDTO.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .postId(comment.getTravelPost().getId())
                .userId(comment.getAuthor().getId())
                .replies(convertChildComments(comment.getChildTravelComments()))
                .build();
    }

    private static List<CommentResponseDTO.CommentDTO> convertChildComments(List<TravelComment> childComments) {
        if (childComments == null || childComments.isEmpty()) {
            return new ArrayList<>();
        }
        return childComments.stream()
                .map(TravelCommentConverter::toCommentDTO)
                .collect(Collectors.toList());
    }

    public static CommentResponseDTO.CommentsDTO toCommentsDTO(List<TravelComment> comments) {
        List<CommentResponseDTO.CommentDTO> rootComments = comments.stream()
                .filter(comment -> comment.getParentTravelComment() == null)
                .map(TravelCommentConverter::toCommentDTO)
                .collect(Collectors.toList());

        return CommentResponseDTO.CommentsDTO.builder()
                .comments(rootComments)
                .build();
    }
}
