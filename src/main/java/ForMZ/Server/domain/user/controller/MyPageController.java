package ForMZ.Server.domain.user.controller;

import ForMZ.Server.domain.comment.dto.AllCommentRes;
import ForMZ.Server.domain.post.dto.AllPostRes;
import ForMZ.Server.domain.user.dto.ProfileRes;
import ForMZ.Server.domain.user.dto.ProfileUpdateReq;
import ForMZ.Server.domain.user.service.ProfileService;
import ForMZ.Server.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ForMZ.Server.domain.user.constant.ProfileConstant.MyPageResponseMessage.*;

@RestController
@RequestMapping("/my-page")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MyPageController {

    private final ProfileService profileService;

    /**
     * 프로필 조회
     */
    @GetMapping("/profiles")
    public ResponseEntity getProfile(){
        ProfileRes res = profileService.getProfile();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), GET_PROFILE_SUCCESS.getMessage(), res));
    }

    /**
     * 프로필 수정
     */
    @PatchMapping("/profiles")
    public ResponseEntity updateProfile(@RequestBody ProfileUpdateReq profileUpdateReq){
        ProfileRes res = profileService.updateProfile(profileUpdateReq);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), UPDATE_PROFILE_SUCCESS.getMessage(), res));
    }

    /**
     * 자신이 작성한 커뮤니티 게시글 조회
     */
    @GetMapping("/posts")
    public ResponseEntity getPostsWrittenByUser(){
        AllPostRes resList = profileService.getPostsWrittenByUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), GET_POSTS_WRITTEN_BY_USER_SUCCESS.getMessage(), resList));
    }

    /**
     * 자신이 작성한 댓글 조회
     */
    @GetMapping("/comments")
    public ResponseEntity getCommentsWrittenByUser(){
        AllCommentRes resList = profileService.getCommentsWrittenByUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), GET_COMMENTS_WRITTEN_BY_USER_SUCCESS.getMessage(), resList));
    }

    /**
     * 자신이 북마크한 게시글 조회
     */
    @GetMapping("/bookmark/posts")
    public ResponseEntity getPostBookmarked(){
        AllPostRes resList = profileService.getPostsBookmarked();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.create(HttpStatus.OK.value(), GET_POSTS_BOOKMARKED_SUCCESS.getMessage(), resList));
    }
}
