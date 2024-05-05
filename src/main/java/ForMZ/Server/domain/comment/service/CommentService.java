package ForMZ.Server.domain.comment.service;

import ForMZ.Server.domain.comment.dto.*;

public interface CommentService {
    void createComment(CommentReq commentReq);

    AllCommentRes getComment(Long postId);

    CommentUpdateRes updateComment(Long commentId, CommentUpdateReq cmtUpdateReq);

    void deleteComment(Long commentId);
}
