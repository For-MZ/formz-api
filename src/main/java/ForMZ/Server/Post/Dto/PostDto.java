package ForMZ.Server.Post.Dto;

import ForMZ.Server.User.Dto.UserDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDto {

    Long postId;
    String title;
    UserDto writer;
    String categoryName;
    LocalDateTime createDate;
    LocalDateTime lastModifiedDate;
    int likeCnt;
    int views;
    int commentCnt;

}
