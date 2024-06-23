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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private Category categories;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="house_id")
    private House house;

    @Enumerated(EnumType.STRING)
    private PostType type;
    public void setUser(User user){
        this.user = user;
        user.getPostList().add(this);
    }

    public void setCategories(Category categories){
        this.categories = categories;
        categories.getPostsList().add(this);
    }

    public Post(String title, String text, int view_count, int like_count, PostType type) {
        this.title = title;
        this.content = text;
        this.view_count = view_count;
        this.like_count = like_count;
        this.type = type;
    }

    public void changePost(String title, String text) {
        this.title = title;
        this.content = text;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
