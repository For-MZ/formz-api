package ForMZ.Server.domain.user.entity;

import ForMZ.Server.domain.comment.entity.Comment;
import ForMZ.Server.domain.post.entity.Post;
import ForMZ.Server.global.entity.BaseEntity;
import ForMZ.Server.global.oauth.dto.OAuthUserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column
    private String profileImageUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SignType signType;

    @Column
    private String socialId;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Post> posts = new ArrayList<>();

    // TODO : OAuth 로그인 유저의 닉네임 설정
    public static User toEntity(OAuthUserInfo oAuthUserInfo) {
        return User.builder()
                .email(oAuthUserInfo.getEmail())
//                .nickName()
                .profileImageUrl(oAuthUserInfo.getImageUrl())
                .signType(oAuthUserInfo.getSocialType())
                .socialId(oAuthUserInfo.getSocialId())
                .build();
    }

    public void updateProfile(String email, String password, String nickName, String profileImage) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public void updateOAuthInfo(OAuthUserInfo oAuthUserInfo) {
        this.email = oAuthUserInfo.getEmail();
    }
}