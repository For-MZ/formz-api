package ForMZ.Server.Comment.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyDto {
    String Comment;
    String cmtWriter;
}
