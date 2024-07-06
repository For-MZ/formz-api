package ForMZ.Server.SearchHistory.Service;

import ForMZ.Server.SearchHistory.Dto.searchHistoryDto;
import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.SearchHistory.Repository.SearchHistoryRepository;
import ForMZ.Server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public List<searchHistoryDto> FindUserSearchHistory(Long userId) throws Exception {
        List<SearchHistory> searchHistories = searchHistoryRepository.UserSearchHistory(userId);
        if(searchHistories.isEmpty()) throw new Exception("검색기록이 존재하지 않습니다");
        return searchHistories.stream().map(s->{
            return new searchHistoryDto(s.getId(),s.getWord());
        }).toList();
    }
    public void DeleteSearchHistory(Long searchId) throws Exception {
        Optional<SearchHistory> searchHistory = searchHistoryRepository.findById(searchId);
        if(searchHistory.isEmpty()) throw new Exception("검색기록이 존재하지 않습니다");
        searchHistoryRepository.delete(searchHistory.get());
    }
    @Transactional
    public void save(SearchHistory searchHistory){
        searchHistoryRepository.save(searchHistory);
    }
}

