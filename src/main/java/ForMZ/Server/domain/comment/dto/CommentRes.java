package ForMZ.Server.domain.comment.dto;

import ForMZ.Server.domain.comment.dto.child.ChildCommentRes;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentRes {
    private String comment;
    private String cmtWriter;
    private boolean cmtLiked;
    private int cmtLikeCnt;
    List<ChildCommentRes> childCmts = new ArrayList<>();
}
