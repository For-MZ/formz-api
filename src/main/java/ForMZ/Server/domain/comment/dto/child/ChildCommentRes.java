package ForMZ.Server.domain.comment.dto.child;

import lombok.Data;

@Data
public class ChildCommentRes {
    private String comment;
    private String cmtWriter;
    private boolean cmtLiked;
    private int cmtLikeCnt;
}
