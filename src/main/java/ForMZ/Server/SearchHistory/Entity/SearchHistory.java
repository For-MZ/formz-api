package ForMZ.Server.SearchHistory.Entity;

import ForMZ.Server.User.Entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String word;

    public SearchHistory(String word) {
        this.word = word;
    }

    public void setUser(User user){
        this.user = user;
        user.getSearchHistories().add(this);
    }
}