package ForMZ.Server.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ResPostsDto {
    private String category;
    private String word;
    private int page;


}
