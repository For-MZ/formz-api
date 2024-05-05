package ForMZ.Server.domain.commentLike.repository;

import ForMZ.Server.domain.comment.entity.Comment;
import ForMZ.Server.domain.commentLike.entity.CommentLike;
import ForMZ.Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
     Optional<CommentLike> findCommentLikeByUserAndComment(User user, Comment comment);
}
