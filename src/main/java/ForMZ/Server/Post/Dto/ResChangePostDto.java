package ForMZ.Server.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResChangePostDto {
    String title;
    String content;
    String categoryName;
}
