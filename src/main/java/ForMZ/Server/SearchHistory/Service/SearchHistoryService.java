package ForMZ.Server.SearchHistory.Service;

import ForMZ.Server.SearchHistory.Dto.searchHistoryDto;
import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.SearchHistory.Repository.SearchHistoryRepository;
import ForMZ.Server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public List<searchHistoryDto> FindUserSearchHistory(Long userId){
        List<SearchHistory> searchHistories = searchHistoryRepository.UserSearchHistory(userId);
        return searchHistories.stream().map(s->{
            return new searchHistoryDto(s.getId(),s.getWord());
        }).toList();
    }
    public void DeleteSearchHistory(Long searchWordId) throws Exception {
        Optional<SearchHistory> searchHistory = searchHistoryRepository.findById(searchWordId);
        if(searchHistory.isEmpty()) throw new Exception("존재하지않는 댓글입나다");
        searchHistoryRepository.delete(searchHistory.get());
    }
}

