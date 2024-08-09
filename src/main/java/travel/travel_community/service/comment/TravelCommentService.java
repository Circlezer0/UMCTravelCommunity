package travel.travel_community.service.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.GeneralException;
import travel.travel_community.apiPayload.exception.handler.CommentHandler;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.TravelComment;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.repository.UserRepository;
import travel.travel_community.repository.comment.TravelCommentRepository;
import travel.travel_community.service.TravelPostService;
import travel.travel_community.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelCommentService {

    private final TravelCommentRepository commentRepository;
    private final TravelPostService travelPostService;
    private final UserService userService;

    /**
     * 댓글 작성
     * @param postId 게시글 아이디
     * @param userid 유저 아이디
     * @param content 댓글 내용
     * @param parentCommentId null 이면 일반 댓글, 아니면 대댓글
     * @return 작성된 댓글
     */
    @Transactional
    public TravelComment addComment(Long postId, String userid, String content, Long parentCommentId) {
        TravelPost post = travelPostService.findTravelPostById(postId);
        User user = userService.findUserByUserId(userid);

        TravelComment comment = new TravelComment();
        comment.setContent(content);
        comment.setAuthor(user);
        comment.setTravelPost(post);

        if (parentCommentId != null && parentCommentId != 0L) {
            TravelComment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new CommentHandler(ErrorStatus.PARENT_COMMENT_NOT_FOUND));
            comment.setParentTravelComment(parentComment);
        }

        return commentRepository.save(comment);
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 아이디
     * @param userid 유저 아이디
     */
    @Transactional
    public void deleteComment(Long commentId, String userid) {
        TravelComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getAuthor().getUserid().equals(userid)) {
            // 댓글 작성자가 아닌 경우
            throw new GeneralException(ErrorStatus._NO_AUTHORITY);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public List<TravelComment> getRootComments(Long postId) {
        TravelPost post = travelPostService.findTravelPostById(postId);
        return commentRepository.findTravelCommentsByTravelPostAndParentTravelCommentIsNull(post);
    }

}