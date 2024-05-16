package com.example.demo.Repository.Post;

import com.example.demo.Core.Querydsl4RepositorySupport;
import com.example.demo.Entity.Post;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.Entity.QCategories.categories;
import static com.example.demo.Entity.QPosts.posts;
import static com.example.demo.Entity.QUser.user;

@Repository
@Getter
public class PostRepositoryImpl extends Querydsl4RepositorySupport implements PostRepositoryCustom {
    private final JPAQueryFactory query;
    public PostRepositoryImpl(EntityManager em) {
        super(Post.class);
        this.query = new JPAQueryFactory(em);
    }

    //19,23 서비스에서 처리
    public List<Post> FindPost(String categoryName,List<String> words){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String word : words) {
            booleanBuilder.and(posts.title.like("%" + word + "%"));
        }
        return selectFrom(posts).join(posts.categories,categories).fetchJoin().join(posts.user, user).fetchJoin()
                .where(NameEq(categoryName),booleanBuilder).fetch();
    }
    //20

    public Post FindPostById(Long id){
        return selectFrom(posts).join(posts.categories,categories).fetchJoin().join(posts.user, user).fetchJoin()
                .where(posts.id.eq(id)).fetchOne();
    }
    //21


    private BooleanExpression NameEq(String name) {
        return name!=null ? categories.category_name.eq(name) : null;
    }
}
