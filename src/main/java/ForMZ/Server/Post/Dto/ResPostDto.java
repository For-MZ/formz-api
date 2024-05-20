package ForMZ.Server.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResPostDto {
    String title;
    String content;
    String categoryName;
    int view_count=0;
    int like_count=0;
}
