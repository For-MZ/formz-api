package ForMZ.Server.Comment.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResRepliesDto {
    Long commentId;
    String content;
}
