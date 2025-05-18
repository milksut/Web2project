package com.storius.storius.entities;

import jakarta.persistence.*;

@Entity
public class TranslationVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Translation translation;

    // 1 for upvote, -1 for downvote
    private int voteType;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Translation getTranslation() { return translation; }
    public void setTranslation(Translation translation) { this.translation = translation; }

    public int getVoteType() { return voteType; }
    public void setVoteType(int voteType) { this.voteType = voteType; }
}