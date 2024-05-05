package ForMZ.Server.domain.comment.service;

import ForMZ.Server.domain.comment.dto.*;
import ForMZ.Server.domain.comment.dto.child.ChildCommentRes;
import ForMZ.Server.domain.comment.entity.Comment;
import ForMZ.Server.domain.comment.exception.CommentNotFoundException;
import ForMZ.Server.domain.comment.mapper.CommentMapper;
import ForMZ.Server.domain.comment.repository.CommentRepository;
import ForMZ.Server.domain.commentLike.entity.CommentLike;
import ForMZ.Server.domain.commentLike.repository.CommentLikeRepository;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.post.service.PostService;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ForMZ.Server.global.entity.BaseEntity.ObjectState.ACT;
import static ForMZ.Server.global.entity.BaseEntity.ObjectState.DEL;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final UserService userService;
    private final PostService postService;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 작성
     */
    @Override
    public void createComment(CommentReq commentReq) {
        Post post = postService.getPost(commentReq.getPostId());
        User user = userService.getUser(commentReq.getUserId());

        final String content = commentReq.getComment();
        Comment newComment = new Comment(content, user, post);
        commentRepository.save(newComment);
        newComment.changeState(ACT);
    }

    /**
     * 댓글 조회
     */
    @Override
    public AllCommentRes getComment(Long postId) {
        Post post = postService.getPost(postId);
        //작성한 댓글 리스트
        List<CommentRes> comments = post.getComments().stream()
                .map(comment -> {
                    boolean cmtLiked = false;
                    Optional<CommentLike> commentLike = commentLikeRepository.findCommentLikeByUserAndComment(comment.getUser(), comment);
                    if (commentLike.isPresent()) {
                        cmtLiked = true;
                    }
                    int cmtLikeCnt = comment.getCommentLikes().size();
                    //대댓글 리스트
                    List<ChildCommentRes> childCmts = getFiveChildComments(comment.getId());
                    return CommentMapper.INSTANCE.toCommentRes(cmtLiked, cmtLikeCnt, childCmts, comment);
                }).toList();

        return new AllCommentRes(comments);
    }

    /**
     * 대댓글 상위 5개 가져오기
     */
    private List<ChildCommentRes> getFiveChildComments(Long parentCmtId) {
        PageRequest pageable = PageRequest.of(0, 5);
        List<Comment> childComments = commentRepository.findChildCmtTop5ByCreatedDateDesc(parentCmtId, pageable);
        List<ChildCommentRes> childCmts = childComments.stream()
                .map(c -> {
                    boolean cmtLiked = false;
                    Optional<CommentLike> commentLike = commentLikeRepository.findCommentLikeByUserAndComment(c.getUser(), c);
                    if (commentLike.isPresent()) {
                        cmtLiked = true;
                    }
                    int cmtLikeCnt = c.getCommentLikes().size();
                    return CommentMapper.INSTANCE.toChildCommentRes(cmtLiked, cmtLikeCnt, c);
                }).toList();
        return childCmts;
    }

    /**
     * 댓글 수정
     */
    @Override
    public CommentUpdateRes updateComment(Long commentId, CommentUpdateReq cmtUpdateReq) {
        //TODO : 본인 댓글인지 확인해야함
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

       final String content = cmtUpdateReq.getContent();
       comment.updateComment(content);

        return new CommentUpdateRes(comment.getContent());
    }

    /**
     * 댓글 삭제
     */
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.changeState(DEL);
    }
}
