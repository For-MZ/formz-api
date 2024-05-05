package ForMZ.Server.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostReq {

    private String category;
    @NotBlank
    private String title;
    @NotBlank
    private String text;

    private String imageUrl;
}
