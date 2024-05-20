package ForMZ.Server.SearchHistory.Repository;

import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.User.Entity.User;

import java.util.List;

public interface SearchHistoryRepositoryCustom {
    List<SearchHistory> UserSearchHistory(Long userId);
}
