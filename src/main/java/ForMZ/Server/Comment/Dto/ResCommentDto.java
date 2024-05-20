package ForMZ.Server.Comment.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResCommentDto {
    Long userId;
    Long postId;
    String content;
}
