package ForMZ.Server.Comment.Repository;

import ForMZ.Server.Comment.Entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {
    List<Comment> findUserComment(Long userId);
    Optional<Comment> findWithPost(Long commentId);
    List<Comment> reply(Long Comment_id);
    List<Comment> postAllComment(Long postId);
}
