package ForMZ.Server.SearchHistory.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class searchHistoryDto {
    private Long searchWordId;
    private String searchWord;
}
