package ForMZ.Server.domain.comment.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CommentConstant {

    @Getter
    @RequiredArgsConstructor
    public enum CResponseMessage {
        CREATE_SUCCESS("댓글이 성공적으로 작성되었습니다"),
        GET_SUCCESS("모든 댓글이 성공적으로 조회되었습니다"),
        UPDATE_SUCCESS("댓글이 성공적으로 수정되었습니다"),
        DELETE_SUCCESS("댓글이 성공적으로 삭제되었습니다");

        private final String message;
    }
}
