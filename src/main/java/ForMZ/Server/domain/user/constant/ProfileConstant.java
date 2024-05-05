package ForMZ.Server.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ProfileConstant {

    @Getter
    @RequiredArgsConstructor
    public enum MyPageResponseMessage{
        GET_PROFILE_SUCCESS("프로필이 성공적으로 조회되었습니다."),
        UPDATE_PROFILE_SUCCESS("프로필이 성공적으로 수정되었습니다."),
        GET_POSTS_WRITTEN_BY_USER_SUCCESS("자신이 작성한 게시글이 성공적으로 조회되었습니다."),
        GET_COMMENTS_WRITTEN_BY_USER_SUCCESS("자신이 작성한 댓글이 성공적으로 조회되었습니다."),
        GET_POSTS_BOOKMARKED_SUCCESS("북마크된 게시글이 성공적으로 조회되었습니다.");

        private final String message;
    }
}
