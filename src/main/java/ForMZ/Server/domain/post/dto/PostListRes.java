package ForMZ.Server.domain.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PostListRes {
    private Long postId;
    private String title;
    private String category;
    private String nickName;
    private int likeCnt;
    private int viewCnt;
    private int commentCnt;

    @Builder
    public PostListRes(Long postId, String title, String category, String nickName, int likeCnt, int viewCnt, int commentCnt) {
        this.postId = postId;
        this.title = title;
        this.category = category;
        this.nickName = nickName;
        this.likeCnt = likeCnt;
        this.viewCnt = viewCnt;
        this.commentCnt = commentCnt;
    }
}
