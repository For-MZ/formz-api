package ForMZ.Server.domain.comment.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentReq {
    Long postId;

    Long userId;

    @NotBlank
    String comment;

}
