package ForMZ.Server.Post.Repository;

import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.Core.Querydsl4RepositorySupport;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.Post.Entity.PostType;
import ForMZ.Server.Post.Entity.QHouse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static ForMZ.Server.BookMark.Entity.QBookMark.bookMark;
import static ForMZ.Server.BookMark.Entity.QBookMarkPost.bookMarkPost;
import static ForMZ.Server.Category.Entity.QCategory.category;
import static ForMZ.Server.Comment.Entity.QComment.comment;
import static ForMZ.Server.Post.Entity.QHouse.house;
import static ForMZ.Server.Post.Entity.QPost.post;
import static ForMZ.Server.User.Entity.QUser.user;


@Repository
@Getter
public class PostRepositoryImpl extends Querydsl4RepositorySupport implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public PostRepositoryImpl(EntityManager em) {
        super(Post.class);
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<Post> FindPost(@Nullable String categoryName, List<String> words, final int startPage,final int PageSize){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String word : words) {
            booleanBuilder.and(post.title.like("%" + word + "%"));
        }
        return selectFrom(post).join(post.categories, category).fetchJoin().join(post.user, user).fetchJoin()
                .where(NameEq(categoryName),booleanBuilder,post.type.eq(PostType.posts)).orderBy(post.createdDate.desc()).offset(startPage)
                .limit(PageSize).fetch();
    }
    //20

    public Optional<Post> FindPostById(Long id){
        return Optional.ofNullable(selectFrom(post).join(post.categories, category).fetchJoin().join(post.user, user).fetchJoin()
                .where(post.id.eq(id)).fetchOne());
    }
    //21

    public List<Post> FindBestPost(Pageable pageable){
        return selectFrom(post).join(post.categories, category).fetchJoin().join(post.user, user).fetchJoin()
                .where(post.type.eq(PostType.posts))
                .orderBy(post.view_count.desc(), post.like_count.desc()).limit(pageable.getPageSize()).fetch();
    }
    //24


    public List<Post> findUserPost(Long userId){
        return selectFrom(post).join(post.user, user).fetchJoin().leftJoin(post.commentList, comment).fetchJoin().leftJoin(post.categories, category).fetchJoin().where(user.id.eq(userId),post.type.eq(PostType.posts)).fetch();
    }
    public List<BookMarkPost> BookMarkPostList(Long bookMarkId){
        return select(bookMarkPost).from(bookMark).join(bookMark.bookMarkPostList,bookMarkPost).fetch();
    }

    public List<Post> findAllById(List<Long> id){
        return selectFrom(post).where(post.id.in(id)).fetch();
    }

    public List<Post> BookMark_Post(List<Long> bookMarkPostId){
        return select(post).from(bookMarkPost).join(bookMarkPost.posts,post).where(bookMarkPost.id.in(bookMarkPostId)).fetch();
    }
    private BooleanExpression NameEq(String name) {
        return name!=null ? category.categoryName.eq(name) : null;
    }

    //11,12,13,17,19,20,21,22,23,24,27,28,29
    //18,26,30,32~35

    public List<Post> getDuplicationHouse() {
        QHouse qHouse = house;
        List<Long> maxIds = queryFactory
                .select(qHouse.id.max())
                .from(qHouse)
                .groupBy(qHouse.hsmpSn, qHouse.insttNm, qHouse.brtcNm,
                        qHouse.signguNm, qHouse.hsmpNm, qHouse.rnAdres,
                        qHouse.competDe, qHouse.hshldCo, qHouse.suplyTyNm,
                        qHouse.styleNm, qHouse.suplyPrvuseAr, qHouse.suplyCmnuseAr,
                        qHouse.houseTyNm, qHouse.heatMthdDetailNm, qHouse.buldStleNm,
                        qHouse.elvtrInstlAtNm, qHouse.parkngCo, qHouse.bassRentGtn,
                        qHouse.bassMtRntchrg, qHouse.bassCnvrsGtnLmt)
                .fetch();

        return queryFactory
                .selectFrom(post)
                .join(post.house,house)
                .where(house.id.in(maxIds))
                .fetch();
    }

    public List<Post> getAllHouse(){
        return selectFrom(post).where(post.type.eq(PostType.house)).join(post.house,house).fetchJoin().fetch();
    }
}
