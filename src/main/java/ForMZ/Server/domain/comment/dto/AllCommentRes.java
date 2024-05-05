package ForMZ.Server.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllCommentRes {
    private List<CommentRes> comments = new ArrayList<>();
}
