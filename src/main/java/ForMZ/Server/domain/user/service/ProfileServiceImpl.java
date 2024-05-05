package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.bookmark.entity.Bookmark;
import ForMZ.Server.domain.comment.dto.AllCommentRes;
import ForMZ.Server.domain.comment.entity.Comment;
import ForMZ.Server.domain.comment.mapper.CommentMapper;
import ForMZ.Server.domain.post.dto.AllPostRes;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.domain.post.mapper.PostMapper;
import ForMZ.Server.domain.user.dto.ProfileRes;
import ForMZ.Server.domain.user.dto.ProfileUpdateReq;
import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;

    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;


    /**
     * 유저 프로필 조회
     */
    @Override
    public ProfileRes getProfile(){
        User user = userService.getCurrentUser();
        return userMapper.userToProfileRes(user);
    }

    /**
     * 유저 프로필 수정
     */
    @Override
    public ProfileRes updateProfile(ProfileUpdateReq profileUpdateReq){
        User user = userService.getCurrentUser();

        final String email = profileUpdateReq.getEmail();
        final String password = profileUpdateReq.getPassword();
        final String nickName = profileUpdateReq.getNickName();
        final String profileImage = profileUpdateReq.getProfileImage();

        user.updateProfile(email, password, nickName, profileImage);

        return userMapper.userToProfileRes(user);
    }

    /**
     * 유저가 작성한 게시글 조회
     */
    @Override
    public AllPostRes getPostsWrittenByUser(){
        User user = userService.getCurrentUser();

        List<Post> posts = user.getPosts();
        return new AllPostRes(postMapper.postListToAllPostRes(posts));
    }

    /**
     * 유저가 작성한 댓글 조회
     */
    @Override
    public AllCommentRes getCommentsWrittenByUser(){
        User user = userService.getCurrentUser();

        List<Comment> comments = user.getComments();
        return commentMapper.commentsToCommentResList(comments);
    }

    /**
     * 유저가 북마크한 게시글 조회
     */
    @Override
    public AllPostRes getPostsBookmarked(){
        User user = userService.getCurrentUser();

        List<Post> ret = user.getBookmarks().stream()
                .sorted(Comparator.comparing(Bookmark::getLastModifiedDate).reversed())
                .map(Bookmark::getPost)
                .collect(Collectors.toList());

        return new AllPostRes(postMapper.postListToAllPostRes(ret));
    }
}