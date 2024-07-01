package ForMZ.Server.User.Entity;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.Comment.Entity.Comment;
import ForMZ.Server.Core.BaseEntity;
import ForMZ.Server.Post.Entity.Post;
import ForMZ.Server.SearchHistory.Entity.SearchHistory;
import ForMZ.Server.User.Dto.ChangeProFileDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;


    @NotNull
    private String password;

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 10)
    private String nickname;

    @Column(name = "social_id")
    @Size(max = 100)
    private String socialId;

    @Column(name = "profile_image_url")
    private String profileImageUrl;


    @Column(name = "withdraw_time")
    @CreationTimestamp private LocalDateTime withdrawTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SearchHistory> searchHistories = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_mark_id")
    private BookMark bookMark;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void changeProfile(ChangeProFileDto changeProFile){
        this.email = changeProFile.getEmail();
        this.nickname = changeProFile.getNickname();
        this.password = changeProFile.getPassword();
        this.profileImageUrl = changeProFile.getProfileImage();
    }
    public void settingBookMark(BookMark bookMark){
        this.bookMark = bookMark;
        bookMark.settingUser(this);
    }

    public User(String password, String email, String nickname, String profileImageUrl) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void setUserSocialId(String socialId) {
        this.socialId = socialId;
    }
}