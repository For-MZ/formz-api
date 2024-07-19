package ForMZ.Server.Comment.Controller;

import ForMZ.Server.Comment.Dto.*;
import ForMZ.Server.Comment.Service.CommentService;
import ForMZ.Server.Configuration.JwtTokenUtil;
import ForMZ.Server.Core.Dto.JoinDto;
import ForMZ.Server.User.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/api/user/comment")
    public ResponseEntity<List<CommentDto>> userComment(HttpServletRequest request) throws Exception {
        try {
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            Long userId = userService.findUserProfile(accessToken).getId();
            List<CommentDto> userComment = commentService.findUserComment(userId);
            return ResponseEntity.status(200).body(userComment);
        }
        catch (Exception e){
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/community/comments")
    public ResponseEntity<JoinDto> userAddComment(@RequestBody ResCommentDto resCommentDto){
        try {
            Long commentId = commentService.addComment(resCommentDto);
            JoinDto joinDto = new JoinDto(commentId,"댓글이 성공적으로 작성되었습니다.");
            return ResponseEntity.status(201).body(joinDto);
        }
        catch (Exception e){
            return ResponseEntity.status(404).build();
        }
    }
    @GetMapping("/community/comments/{post-id}")
    public ResponseEntity<List<CommentDto>> postComment(@PathVariable("post-id") Long postId){
        try {
            List<CommentDto> postComment = commentService.findPostComment(postId);
            return ResponseEntity.status(200).body(postComment);
        }
        catch (Exception e){
            return ResponseEntity.status(404).build();
        }

    }
    @PatchMapping("/community/comments/{comment-id}")
    public ResponseEntity<JoinDto> commentChange(@PathVariable("comment-id") Long commentId, @RequestBody CommentChangeDto commentChangeDto){
        try {
            System.out.println(commentId);
            Long l = commentService.ChangeComment(commentChangeDto.getContent(), commentId);
            return ResponseEntity.status(200).body(new JoinDto(l,"댓글이 성공적으로 수정되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/community/replies")
    public ResponseEntity<JoinDto> AddReply(@RequestBody ResRepliesDto repliesDto,HttpServletRequest request){
        try {
            String accessToken = jwtTokenUtil.resolveAccessToken(request);
            Long userId = userService.findUserProfile(accessToken).getId();
            Long replyId = commentService.addReplies(repliesDto, userId);
            return ResponseEntity.status(200).body(new JoinDto(replyId,repliesDto.getContent()));
        }
        catch (Exception e){
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/community/replies/{commentId}")
    public ResponseEntity<List<ReplyDto>> Reply(@PathVariable("commentId") Long commentId){
        try {
            List<ReplyDto> reply = commentService.commentReplies(commentId);
            return ResponseEntity.status(200).body(reply);
        }
        catch (Exception e){
            return ResponseEntity.status(404).build();
        }

    }
    @PatchMapping("/community/replies/{replyId}")
    public ResponseEntity<String> commentReply(@PathVariable("replyId") Long replyId,@RequestBody CommentChangeDto commentChangeDto){
        try {
            Long l = commentService.ChangeComment(commentChangeDto.getContent(), replyId);
            return ResponseEntity.status(200).body("대댓글이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
    @DeleteMapping("/community/comments/{comment-id}")
    public ResponseEntity<String> deleteChange(@PathVariable("comment-id") Long commentId){
        try {
            commentService.DeleteComment(commentId);
            return ResponseEntity.status(200).body("댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
    @DeleteMapping("/community/replies/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable("replyId") Long replyId){
        try {
            commentService.DeleteComment(replyId);
            return ResponseEntity.status(200).body("대댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
}
