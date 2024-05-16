package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id")
    private Long id;

    @OneToOne(mappedBy = "bookMarks",cascade = CascadeType.ALL )
    private User user;

    @OneToMany(mappedBy = "bookMarks")
    private List<BookMarkPost> bookMarkPostList = new ArrayList<>();

    public void changeUser(User user){
        this.user = user;
    }

}
