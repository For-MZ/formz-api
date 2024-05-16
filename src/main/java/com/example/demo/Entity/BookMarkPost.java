package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
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
}
