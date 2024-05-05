package ForMZ.Server.domain.comment.mapper;

import ForMZ.Server.domain.comment.dto.AllCommentRes;
import ForMZ.Server.domain.comment.dto.CommentRes;
import ForMZ.Server.domain.comment.dto.child.ChildCommentRes;
import ForMZ.Server.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "comment.content", target = "comment")
    @Mapping(source = "comment.user.nickName", target = "cmtWriter")
    CommentRes toCommentRes(boolean cmtLiked, int cmtLikeCnt,
                            List<ChildCommentRes> childCmts, Comment comment);

    @Mapping(source = "comment.content", target = "comment")
    @Mapping(source = "comment.user.nickName", target = "cmtWriter")
    ChildCommentRes toChildCommentRes(boolean cmtLiked, int cmtLikeCnt, Comment comment);

    /**
     *  POST Mapper에서 Response에 List<comment> -> List<commentRes> 매핑 에러 해결을 위해 임시 생성
     *  TODO: 현재 CommentServiceImpl 에서 댓글 조회 부분을 인용해 사용 중인데 검토 필요
     */
    default AllCommentRes  commentsToCommentResList(List<Comment> comments){
        List<CommentRes> commentList = comments.stream()
                .map(comment -> {
                    boolean cmtLiked = false;   // TODO: Mapper에서 commentLike 가져오기
//                    Optional<CommentLike> commentLike = commentLikeRepository.findCommentLikeByUserAndComment(comment.getUser(), comment);
//                    if (commentLike.isPresent()) {
//                        cmtLiked = true;
//                    }
                    int cmtLikeCnt = comment.getCommentLikes().size();
                    //대댓글 리스트
//                    List<ChildCommentRes> childCmts = getFiveChildComments(comment);
                    List<ChildCommentRes> childCmts = new ArrayList<>();    //  TODO: Mapper에서 대댓글 가져오기
                    return toCommentRes(cmtLiked, cmtLikeCnt, childCmts, comment);
                }).toList();

        return new AllCommentRes(commentList);
    }
}
