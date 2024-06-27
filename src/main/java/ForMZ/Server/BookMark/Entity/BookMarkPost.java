package ForMZ.Server.BookMark.Entity;

import ForMZ.Server.Post.Entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class BookMarkPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_mark_id")
    private BookMark bookMarks;

    public BookMarkPost(Post posts, BookMark bookMarks) {
        this.posts = posts;
        this.bookMarks = bookMarks;
    }
}
