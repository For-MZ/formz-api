package ForMZ.Server.Comment.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    Long commentId;
    String content;
    LocalDateTime createDate;
    LocalDateTime lastModifiedDate;
}