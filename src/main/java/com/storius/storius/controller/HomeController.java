package com.storius.storius.controller;

import com.storius.storius.entities.Book;
import com.storius.storius.services.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private bookService bookService;

    @GetMapping("/")
    public String getHome(Model model) {
        // Fetch featured books, in-progress books, and trending books
        List<Book> featuredBooks = bookService.getFeaturedBooks();
        List<Book> inProgressBooks = bookService.getInProgressBooks();
        List<Book> trendingBooks = bookService.getTrendingBooks();

        // Add data to the model
        model.addAttribute("featuredBooks", featuredBooks);
        model.addAttribute("inProgressBooks", inProgressBooks);
        model.addAttribute("trendingBooks", trendingBooks);

        return "main";
    }

    @GetMapping("/audiobook")
    String getbook(Model model) {
        model.addAttribute("book_name", "my book");
        return "audiobook";
    }
}
