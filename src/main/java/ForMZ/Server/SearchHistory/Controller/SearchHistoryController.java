package ForMZ.Server.SearchHistory.Controller;

import ForMZ.Server.Comment.Dto.CommentDto;
import ForMZ.Server.SearchHistory.Dto.searchHistoryDto;
import ForMZ.Server.SearchHistory.Service.SearchHistoryService;
import ForMZ.Server.User.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchHistoryController {
    private final UserService userService;
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/searchWords/{userId}")
    public ResponseEntity<List<searchHistoryDto>> userSearchHistory(@PathVariable("userId") Long userId) {
        try {
            List<searchHistoryDto> searchHistoryDtos = searchHistoryService.FindUserSearchHistory(userId);
            return ResponseEntity.status(200).body(searchHistoryDtos);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/searchWords/{searchWordId}")
    public ResponseEntity<String> deleteSearchHistory(@PathVariable("searchWordId") Long searchWordId) {
        try {
            searchHistoryService.DeleteSearchHistory(searchWordId);
            return ResponseEntity.status(200).body("검색어가 정상적으로 삭제되었습니다.");
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
