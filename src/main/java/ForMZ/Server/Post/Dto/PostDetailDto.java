package ForMZ.Server.Post.Dto;

import ForMZ.Server.User.Dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDetailDto {

    Long postId;
    String title;
    String content;
    UserDto writer;
    String categoryName;
    LocalDateTime createDate;
    LocalDateTime lastModifiedDate;
    int likeCnt;
    int views;
    int commentCnt;

}
