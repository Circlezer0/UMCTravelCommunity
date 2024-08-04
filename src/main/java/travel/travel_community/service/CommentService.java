package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.travel_community.entity.User;
import travel.travel_community.entity.posts.Comment;
import travel.travel_community.entity.posts.ReComment;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.repository.CommentRepository;
import travel.travel_community.repository.ReCommentRepository;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.repository.UserRepository;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private final TravelPostRepository travelPostRepository;
    private final UserRepository userRepository;

    @Transactional
    //댓글 작성하는 메서드
    public Comment createComment(Long postId, Long userId, String content) {
        TravelPost travelPost = travelPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setTravelPost(travelPost);
        comment.setAuthor(user);
        comment.setCreatedDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    //대댓글 작성 메서드
    @Transactional
    public ReComment createReply(Long userId, Long commentId, String content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        ReComment reply = new ReComment();
        reply.setContent(content);
        reply.setAuthor(user);
        reply.setParentComment(parentComment);

        return reCommentRepository.save(reply);
    }
}
