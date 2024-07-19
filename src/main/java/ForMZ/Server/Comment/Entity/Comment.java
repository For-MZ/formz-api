package ForMZ.Server.Comment.Entity;

import ForMZ.Server.Core.BaseEntity;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.User.Entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private int like_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private Post post;

    public void setParent(Comment comments){
        this.parent = comments;
        comments.children.add(this);
    }

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.like_count = 0;
        this.user = user;
        this.post = post;
        user.getComments().add(this);
        post.getCommentList().add(this);
    }

    public void ChangeContent(String content) {
        this.content = content;
    }

}
