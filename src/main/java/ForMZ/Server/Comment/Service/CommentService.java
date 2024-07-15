package ForMZ.Server.Comment.Service;

import ForMZ.Server.Comment.Dto.CommentDto;
import ForMZ.Server.Comment.Dto.ReplyDto;
import ForMZ.Server.Comment.Dto.ResCommentDto;
import ForMZ.Server.Comment.Dto.ResRepliesDto;
import ForMZ.Server.Comment.Entity.Comment;
import ForMZ.Server.Comment.Repository.CommentRepository;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.Post.Repository.PostRepository;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<CommentDto> findUserComment(Long userId){
        List<Comment> userComment = commentRepository.findUserComment(userId);
        return userComment.stream().map(c->{
            return new CommentDto(c.getId(),c.getContent(),c.getCreatedDate(),c.getLastModifiedDate());
        }).toList();
    }
    //12번
    public List<CommentDto> findPostComment(Long postId) throws Exception {
        if(postRepository.findById(postId).isEmpty()){
            throw new Exception("존재하지않는 게시물입니다");
        };
        List<Comment> postComment = commentRepository.postAllComment(postId);
        return postComment.stream().map(c->{
            return new CommentDto(c.getId(),c.getContent(),c.getCreatedDate(),c.getLastModifiedDate());
        }).toList();
    }
    @Transactional
    public Long addComment(ResCommentDto addCommentDto) throws Exception {
        Optional<User> user = userRepository.findById(addCommentDto.getUserId());
        if(user.isEmpty()) throw new Exception("존재하지않는 사용자입니다");
        Optional<Post> post = postRepository.findById(addCommentDto.getPostId());
        if(post.isEmpty()) throw new Exception("존재하지않는 게시물입니다");
        Comment comment = new Comment(addCommentDto.getContent(),user.get(),post.get());
        commentRepository.save(comment);
        return comment.getId();
    }
    @Transactional
    public void DeleteComment(Long commentId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) throw new Exception("존재하지않는 댓글입니다");
        commentRepository.delete(comment.get());
    }//30,35
    @Transactional
    public Long ChangeComment(String content, Long commentId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) throw new Exception("존재하지않는 댓글입니다");
        Comment findComment = comment.get();
        findComment.ChangeContent(content);

        return findComment.getId();
    }
    //29,34
    @Transactional
    public Long addReplies(ResRepliesDto resRepliesDto, Long UserId) throws Exception {
        Optional<Comment> findComment = commentRepository.findWithPost(resRepliesDto.getCommentId());
        if(findComment.isEmpty()) throw new Exception("존재하지않는 댓글입니다");
        Optional<Post> post = postRepository.findById(findComment.get().getPost().getId());
        if (post.isEmpty()) throw new Exception("존재하지않는 게시글입니다");
        Optional<User> user = userRepository.findById(UserId);
        if (user.isEmpty()) throw new Exception("존재하지않는 사용자입니다");
        Comment replies = new Comment(resRepliesDto.getContent(),user.get(),post.get());
        replies.setParent(findComment.get());
        commentRepository.save(replies);
        return replies.getId();
    }
    public List<ReplyDto> commentReplies(Long commentId) throws Exception {
        List<Comment> reply = commentRepository.reply(commentId);
        if(reply.isEmpty()) throw new Exception("대댓글이 없거나 원댓글이 존재하지않습니다.");
        return reply.stream().map(r-> {
                    return new ReplyDto(r.getContent(),r.getUser().getNickname());
                }
        ).toList();
    }
    public CommentDto findById(Long id) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if(commentOptional.isEmpty()){
            throw new Exception("존재하지않는 댓글 입니다");
        }
        Comment comment = commentOptional.get();
        return new CommentDto(comment.getId(),comment.getContent(),comment.getCreatedDate(),comment.getLastModifiedDate());
    }
}

