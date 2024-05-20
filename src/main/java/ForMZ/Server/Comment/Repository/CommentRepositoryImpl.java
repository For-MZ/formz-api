package ForMZ.Server.Comment.Repository;

import ForMZ.Server.Comment.Entity.Comment;
import ForMZ.Server.Core.Querydsl4RepositorySupport;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static ForMZ.Server.Comment.Entity.QComment.comment;
import static ForMZ.Server.Post.Entity.QPost.post;
import static ForMZ.Server.User.Entity.QUser.user;


@Repository
@Getter
public class CommentRepositoryImpl extends Querydsl4RepositorySupport implements CommentRepositoryCustom {
    private final JPAQueryFactory query;
    public CommentRepositoryImpl(EntityManager em) {
        super(Comment.class);
        this.query = new JPAQueryFactory(em);
    }

    public Optional<Comment> findWithPost(Long commentId){
        return Optional.ofNullable(selectFrom(comment).join(comment.post,post).fetchJoin().where(comment.id.eq(commentId)).fetchOne());
    }
    public List<Comment> findUserComment(Long userId){
        return selectFrom(comment).join(comment.user,user).fetchJoin().where(user.id.eq(userId)).fetch();
    }
    public List<Comment> reply(Long Comment_id){
        return selectFrom(comment).where(comment.parent.id.eq(Comment_id)).join(comment.user,user).fetchJoin().fetch();
    }
    public List<Comment> postAllComment(Long postId){
        return selectFrom(comment).join(comment.post).where(post.id.eq(postId)).fetch();
    }
}
