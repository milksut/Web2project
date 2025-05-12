package com.storius.storius.controller;

import com.storius.storius.entities.Book;
import com.storius.storius.entities.Comment;
import com.storius.storius.entities.Translation;
import com.storius.storius.services.CommentService;
import com.storius.storius.services.TranslationService;
import com.storius.storius.services.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BookController
{
    @Autowired
    private bookService bookService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        List<Translation> translations = translationService.getTranslationsBybook(id);
        List<Comment> comments = commentService.getCommentsBybook(id);

        model.addAttribute("book", book);
        model.addAttribute("translations", translations);
        model.addAttribute("comments", comments);

        return "audiobook";
    }
}
