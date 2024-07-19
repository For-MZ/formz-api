package ForMZ.Server.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ResPostDto {
    String title;
    String content;
    String categoryName;
    int view_count=0;
    int like_count=0;

    public ResPostDto(String title, String content, String categoryName) {
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.view_count = 0;
        this.like_count = 0;
    }
}
