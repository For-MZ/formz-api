package ForMZ.Server.domain.post.dto;

import ForMZ.Server.domain.comment.dto.AllCommentRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRes {
    private Long postId;
    private String title;
    private String writer;
    private String categoryName;
    private String text;
    private String imageUrl;
    private boolean bookmarked;
    private boolean liked;
    private int likeCnt;
    private int views;
    private int commentCnt;
    private AllCommentRes comments;
}
