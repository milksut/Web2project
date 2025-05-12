package com.storius.storius.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;  // Many comments can belong to one book

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Many comments can belong to one user

    @Column(nullable = false)
    private String content;  // The content of the comment

    @CreationTimestamp
    @Column
    private LocalDateTime timestamp;  // Timestamp when the comment was posted

    private Integer likes; // Number of likes for the comment
}


