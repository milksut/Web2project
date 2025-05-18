package com.storius.storius.controller;

import com.storius.storius.entities.*;
import com.storius.storius.repos.CommentVoteRepository;
import com.storius.storius.repos.TranslationVoteRepository;
import com.storius.storius.repos.UserRepository;
import com.storius.storius.services.CommentService;
import com.storius.storius.services.TranslationService;
import com.storius.storius.services.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController
{
    @Autowired
    private bookService bookService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Autowired
    private TranslationVoteRepository translationVoteRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id, Model model, @SessionAttribute(value = "user_id", required = false) Long userId) {
        Book book = bookService.findById(id);
        List<Translation> translations = translationService.getTranslationsBybook(id);
        List<Comment> comments = commentService.getCommentsBybook(id);

        List<Comments_votes> commentsWithVotes = new ArrayList<>();
        List<Translations_votes> translationsWithVotes = new ArrayList<>();
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        // Comments with votes
        for (Comment comment : comments) {
            Comments_votes cwv = new Comments_votes();
            cwv.setId(comment.getId());
            cwv.setUser(comment.getUser());
            cwv.setContent(comment.getContent());
            cwv.setLikes(comment.getLikes());
            int vote = 0;
            if (user != null) {
                Optional<CommentVote> voteOpt = commentVoteRepository.findByUserAndComment(user, comment);
                vote = voteOpt.map(CommentVote::getVoteType).orElse(0);
            }
            cwv.setVote(vote);
            commentsWithVotes.add(cwv);
        }

        // Translations with votes
        for (Translation translation : translations) {
            Translations_votes tvw = new Translations_votes();
            tvw.setId(translation.getId());
            tvw.setLanguage(translation.getLanguage());
            tvw.setUpvotes(translation.getUpvotes());
            tvw.setDownvotes(translation.getDownvotes());
            tvw.setTranslatedAudioUrl(translation.getTranslatedAudioUrl());
            tvw.setTextContent(translation.getTextContent());
            tvw.setBook(translation.getBook());
            tvw.setTranslatorName(translation.getTranslatorName());
            int vote = 0;
            if (user != null) {
                Optional<TranslationVote> voteOpt = translationVoteRepository.findByUserAndTranslation(user, translation);
                vote = voteOpt.map(TranslationVote::getVoteType).orElse(0);
            }
            tvw.setVote(vote);
            translationsWithVotes.add(tvw);
        }

        model.addAttribute("book", book);
        model.addAttribute("translations", translationsWithVotes);
        model.addAttribute("comments_votes", commentsWithVotes);

        return "audiobook";
    }

    public class Comments_votes extends Comment {
        private int vote; // 1 = upvote, -1 = downvote, 0 = no vote

        public int getVote() { return vote; }
        public void setVote(int vote) { this.vote = vote; }
    }
    public class Translations_votes extends Translation {
    private int vote; // 1 = upvote, -1 = downvote, 0 = no vote

    public int getVote() { return vote; }
    public void setVote(int vote) { this.vote = vote; }
}
}
