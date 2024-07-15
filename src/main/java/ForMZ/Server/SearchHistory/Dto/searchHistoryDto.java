package ForMZ.Server.SearchHistory.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class searchHistoryDto {
    private Long searchHistoryId;
    private String searchWord;
}
