package ForMZ.Server.BookMark.Entity;

import ForMZ.Server.User.Entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "bookMarks")
    private List<BookMarkPost> bookMarkPostList = new ArrayList<>();

    public void changeUser(User user){
        this.user = user;
        user.getBookMarks().add(this);
    }

}
