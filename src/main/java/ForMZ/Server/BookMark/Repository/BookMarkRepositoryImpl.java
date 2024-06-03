package ForMZ.Server.BookMark.Repository;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.Core.Querydsl4RepositorySupport;
import ForMZ.Server.Post.Entity.Type;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ForMZ.Server.BookMark.Entity.QBookMark.bookMark;
import static ForMZ.Server.BookMark.Entity.QBookMarkPost.bookMarkPost;
import static ForMZ.Server.Post.Entity.QPost.post;
import static ForMZ.Server.User.Entity.QUser.user;

@Repository
public class BookMarkRepositoryImpl extends Querydsl4RepositorySupport implements BookMarkRepositoryCustom {
    private final JPAQueryFactory query;

    public BookMarkRepositoryImpl(EntityManager em) {
        super(BookMark.class);
        this.query = new JPAQueryFactory(em);
    }
    public BookMark UserBookMark(Long UserId){
        return select(bookMark).from(user).join(user.bookMark,bookMark).fetchJoin().fetchOne();
    }

    public List<Long> findBookMarkPostId(Long BookMarkId){
        return select(post.id).from(bookMark).join(bookMark.bookMarkPostList,bookMarkPost).join(bookMarkPost.posts,post).fetchJoin().
                where(bookMark.id.eq(BookMarkId),post.type.eq(Type.posts)
        ).fetch();
    }
    //추후 post로할지 아니면 post와 연관된 user도 함께 가져올지 설정

    public List<Long> findBookMarkHouseId(Long BookMarkId){
        return select(post.id).from(bookMark).join(bookMark.bookMarkPostList,bookMarkPost).join(bookMarkPost.posts,post).fetchJoin().where(
                bookMark.id.eq(BookMarkId),post.type.eq(Type.house)
                //,TypeEq(?) //추후 post에 종류가 붙는다면 동적쿼리로 어떤 종류의 개시물을 가져올지 정함
        ).fetch();
    }
}
