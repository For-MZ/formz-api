package com.example.demo.Repository.User;

import com.example.demo.Core.Querydsl4RepositorySupport;
import com.example.demo.Dto.ChangeProFileDto;
import com.example.demo.Entity.QComments;
import com.example.demo.Entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.demo.Entity.QBookMarks.bookMarks;
import static com.example.demo.Entity.QComments.comments;
import static com.example.demo.Entity.QSearchHistory.searchHistory;
import static com.example.demo.Entity.QUser.user;

@Repository
@Getter
public class UserRepositoryImpl extends Querydsl4RepositorySupport implements UserRepositoryCustom {
    private final JPAQueryFactory query;
    public UserRepositoryImpl(EntityManager em) {
        super(User.class);
        this.query = new JPAQueryFactory(em);
    }

    public User UserWithSearchHistory(Long id){
        return selectFrom(user).join(user.searchHistories,searchHistory).where(user.id.eq(id)).fetchOne();
    }
    //17번
    public User UserWithBookMark(Long id){
        return selectFrom(user).join(user.bookMarksList, bookMarks).where(user.id.eq(id)).fetchOne();
    }
    //13
    public User UserWithPost(Long id){
        return selectFrom(user).join(user.bookMarksList, bookMarks).where(user.id.eq(id)).fetchOne();
    }
    //11
    public User UserWithComment(Long id){
        return selectFrom(user).join(user.comments,comments).where(user.id.eq(id)).fetchOne();
    }
    //12


}
