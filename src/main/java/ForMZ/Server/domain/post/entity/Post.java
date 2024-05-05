package ForMZ.Server.domain.post.entity;

import ForMZ.Server.domain.bookmark.entity.Bookmark;
import ForMZ.Server.domain.category.entity.Category;
import ForMZ.Server.domain.comment.entity.Comment;
import ForMZ.Server.domain.postLike.entity.PostLike;

import ForMZ.Server.domain.user.entity.User;
import ForMZ.Server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private int views = 0;

    @Column
    private String text;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Bookmark> bookmarks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public Post(User user, Category category, String title, String text, String imageUrl){
        setUser(user);
        this.category = category;
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
    }
    private void setUser(User user){
        this.user = user;
        if(!user.getPosts().contains(this)){
            user.getPosts().add(this);
        }
    }

    //  조회 수 증가
    public void viewPlus(){
        this.views++;
    }

    //  게시글 수정
    public void updatePost(Category category, String title, String text, String imageUrl){
        if(category != null){
            this.category = category;
        }
        if(title != null){
            this.title = title;
        }
        if(text != null){
            this.text = text;
        }
        this.imageUrl = imageUrl;
    }

    //  게시글 좋아요 수 반환
    public int getPostLikesCount(){
        return this.postLikes.size();
    }

    //  게시글 댓글 수 반환
    public int getCommentsCount(){
        return this.comments.size();
    }

    //카테고리 이름 반환 함수
    public String getCategoryName() {
        return this.category.getCategoryName().getCategoryName();
    }
}
