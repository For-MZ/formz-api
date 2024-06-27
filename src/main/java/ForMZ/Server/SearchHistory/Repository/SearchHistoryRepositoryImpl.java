package ForMZ.Server.SearchHistory.Repository;

import ForMZ.Server.Core.Querydsl4RepositorySupport;

import ForMZ.Server.SearchHistory.Entity.QSearchHistory;
import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ForMZ.Server.SearchHistory.Entity.QSearchHistory.searchHistory;
import static ForMZ.Server.User.Entity.QUser.user;


@Repository
@Getter
public class SearchHistoryRepositoryImpl extends Querydsl4RepositorySupport implements SearchHistoryRepositoryCustom {
    private final JPAQueryFactory query;
    public SearchHistoryRepositoryImpl(EntityManager em) {
        super(SearchHistory.class);
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<SearchHistory> UserSearchHistory(Long userId) {
        return selectFrom(searchHistory).join(searchHistory.user,user).where(user.id.eq(userId)).fetch();
    }


    //26,32~35
}
