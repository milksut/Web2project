package com.storius.storius.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String coverImageUrl;

    private String author;

    private String language;

    private String audioUrl;

    private Integer rating;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String textContent;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Translation> translations;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ElementCollection
    private List<String> categories; // Categories associated with the book

    private Integer progress; // Progress percentage (e.g., 50 for 50% completed)
}

