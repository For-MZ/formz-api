package ForMZ.Server.Category.Entity;

import ForMZ.Server.Post.Entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categories_id")
    private Long id;

    @Size(max = 10)
    @NotNull
    private String categoryName;

    @OneToMany(mappedBy = "categories")
    private List<Post> postsList = new ArrayList<>();
}
