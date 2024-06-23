package ForMZ.Server.User.Repository;

import ForMZ.Server.Core.Querydsl4RepositorySupport;
import ForMZ.Server.User.Entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static ForMZ.Server.BookMark.Entity.QBookMark.bookMark;
import static ForMZ.Server.User.Entity.QUser.user;


@Repository
@Getter
public class UserRepositoryImpl extends Querydsl4RepositorySupport implements UserRepositoryCustom {
    private final JPAQueryFactory query;
    public UserRepositoryImpl(EntityManager em) {
        super(User.class);
        this.query = new JPAQueryFactory(em);
    }
    @Override
    public Optional<User> findByUserId(String id) {
        return Optional.ofNullable(selectFrom(user).where(user.loginId.eq(id)).fetchOne());
    }

    //17번
    public Optional<User> UserWithBookMark(Long id){
        return Optional.ofNullable(
                selectFrom(user).join(user.bookMark, bookMark).fetchJoin().join(bookMark).where(user.id.eq(id)).fetchOne()
        );
    }
    //13


}
