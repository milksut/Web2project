package com.storius.storius.controller;

import com.storius.storius.entities.User;
import com.storius.storius.services.CommentService;
import com.storius.storius.services.TranslationService;
import com.storius.storius.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @Autowired
    private TranslationService translationService;

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

    @PostMapping("/api/comment/upvote")
    @ResponseBody
    public Map<String, Object> upvoteComment(@RequestBody Map<String, Long> payload, HttpSession session) {
        Long commentId = payload.get("id");
        Long userId = (Long) session.getAttribute("user_id");
        Map<String, Object> response = new HashMap<>();
        int likes = commentService.upvote(commentId, userId);
        response.put("success", true);
        response.put("likes", likes);
        return response;
    }

    @PostMapping("/api/comment/downvote")
    @ResponseBody
    public Map<String, Object> downvoteComment(@RequestBody Map<String, Long> payload, HttpSession session) {
        Long commentId = payload.get("id");
        Long userId = (Long) session.getAttribute("user_id");
        Map<String, Object> response = new HashMap<>();
        int likes = commentService.downvote(commentId, userId);
        response.put("success", true);
        response.put("likes", likes);
        return response;
    }
}
