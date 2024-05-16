package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categories_id")
    private Long id;

    @Size(max = 10)
    @NotNull
    private String category_name;

    @OneToMany(mappedBy = "categories")
    private List<Post> postsList = new ArrayList<>();
}
