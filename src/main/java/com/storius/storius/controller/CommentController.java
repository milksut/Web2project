package com.storius.storius.controller;

import com.storius.storius.entities.User;
import com.storius.storius.services.CommentService;
import com.storius.storius.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/comment")
    public String addComment(@RequestParam("bookId") Long bookId,
                             @RequestParam("contentHidden") String content, HttpSession session)
    {
        Long userId = (Long) session.getAttribute("user_id");

        if (userId != null) {
            Optional<User> userOptional = userService.findById(userId);

            // If the user is found, add it to the model
            userOptional.ifPresent(user -> commentService.addComment(bookId,content,user));
        }

        return "redirect:/book/" + bookId;
    }
}
