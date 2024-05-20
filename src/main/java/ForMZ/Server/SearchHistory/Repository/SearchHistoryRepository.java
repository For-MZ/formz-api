package ForMZ.Server.SearchHistory.Repository;

import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory,Long>,SearchHistoryRepositoryCustom {
}
