package ForMZ.Server.SearchHistory.Repository;

import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.User.Entity.User;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepositoryCustom {
    List<SearchHistory> UserSearchHistory(Long userId);

}
