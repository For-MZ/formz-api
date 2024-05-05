package ForMZ.Server.domain.comment.service;

import ForMZ.Server.domain.comment.dto.*;
import ForMZ.Server.domain.comment.entity.Comment;
import ForMZ.Server.domain.comment.exception.CommentNotFoundException;
import ForMZ.Server.domain.comment.repository.CommentRepository;
import ForMZ.Server.domain.commentLike.repository.CommentLikeRepository;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.post.service.PostService;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.service.UserService;
import ForMZ.Server.global.entity.BaseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private PostService postService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentLikeRepository commentLikeRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("댓글 생성")
    void createComment() {
        //given
        User user = new User();
        Post post = new Post();

        CommentReq cmtReq= new CommentReq();
        cmtReq.setUserId(1L);
        cmtReq.setPostId(1L);
        cmtReq.setComment("new Content");

        given(userService.getUser(anyLong())).willReturn(user);
        given(postService.getPost(anyLong())).willReturn(post);

        //when
        commentService.createComment(cmtReq);

        //then
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 조회")
    void getComments() {
        //given
        User user = new User();
        Post post = new Post();
        new Comment("comment1", user, post);
        new Comment("comment2", user, post);

        given(postService.getPost(anyLong())).willReturn(post);
        given(commentLikeRepository.findCommentLikeByUserAndComment(any(User.class), any(Comment.class)))
                .willReturn(Optional.empty());

        //when
        AllCommentRes response = commentService.getComment(1L);

        //then
        assertEquals(2, response.getComments().size());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() {
        //given
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment("comment", user, post);

        CommentUpdateReq updateReq = new CommentUpdateReq();
        updateReq.setContent("update content");

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        //when
        CommentUpdateRes updateRes = commentService.updateComment(1L, updateReq);

        //then
        assertEquals("update content", updateRes.getContent());
    }

    @Test
    @DisplayName("댓글 수정 - 예외 반환")
    void updateCommentEX() {
        //given
        CommentUpdateReq updateReq = new CommentUpdateReq();

        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());
        //expected
        assertThrows(CommentNotFoundException.class, () -> commentService.updateComment(1L, updateReq));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        //given
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment("comment", user, post);

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        //when
        commentService.deleteComment(1L);

        //then
        assertEquals(BaseEntity.ObjectState.DEL, comment.getObjectState());
    }

    @Test
    @DisplayName("댓글 삭제 - 예외 반환")
    void deleteCommentEX() {
        //given
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());
        //expected
        assertThrows(CommentNotFoundException.class, () -> commentService.deleteComment(1L));
    }
}