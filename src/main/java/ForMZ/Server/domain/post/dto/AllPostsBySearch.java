package ForMZ.Server.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllPostsBySearch {
    private List<PostListRes> postList = new ArrayList<>();
}
