package com.storius.storius.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Builder.Default
    private Integer upvotes = 0;

    @Builder.Default
    private Integer downvotes = 0;

    private String translatedAudioUrl;

    @Lob
    private String textContent;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String translatorName; // Name of the translator
}
