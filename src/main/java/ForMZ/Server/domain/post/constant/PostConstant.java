package ForMZ.Server.domain.post.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PostConstant {

    @Getter
    @RequiredArgsConstructor
    public enum PostResponseMessage {
        CREATE_POST_SUCCESS("게시글이 성공적으로 작성되었습니다."),
        GET_POST_SUCCESS("게시글이 성공적으로 조회되었습니다."),
        GET_POSTS_SUCCESS("모든 게시글 리스트가 성공적으로 조회되었습니다."),
        UPDATE_POST_SUCCESS("게시글이 성공적으로 수정되었습니다."),
        DELETE_POST_SUCCESS("게시글이 성공적으로 삭제되었습니다."),
        GET_POST_BY_SEARCH_SUCCESS("검색 결과 게시글 리스트가 성공적으로 조회되었습니다");

        private final String message;
    }
}
