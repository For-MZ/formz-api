package ForMZ.Server.Post.Entity;

import ForMZ.Server.Category.Entity.Category;
import ForMZ.Server.Comment.Entity.Comment;
import ForMZ.Server.Core.BaseEntity;
import ForMZ.Server.User.Entity.User;
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
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Size(max = 30)
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private int view_count;

    @NotNull
    private int like_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private Category categories;

    public void setUser(User user){
        this.user = user;
        user.getPostList().add(this);
    }

    public void setCategories(Category categories){
        this.categories = categories;
        categories.getPostsList().add(this);
    }

    public Post(String title, String text, int view_count, int like_count) {
        this.title = title;
        this.content = text;
        this.view_count = view_count;
        this.like_count = like_count;
    }

    public void changePost(String title, String text) {
        this.title = title;
        this.content = text;
    }
}
