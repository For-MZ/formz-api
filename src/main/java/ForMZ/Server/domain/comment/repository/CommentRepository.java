package ForMZ.Server.domain.comment.repository;

import ForMZ.Server.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c where c.commentParent.id = :id ORDER BY c.createdDate DESC")
    List<Comment> findChildCmtTop5ByCreatedDateDesc(@Param(value = "id") Long commentId,  Pageable pageable);
}
