package ForMZ.Server.SearchHistory.Repository;

import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory,Long>,SearchHistoryRepositoryCustom {
}
