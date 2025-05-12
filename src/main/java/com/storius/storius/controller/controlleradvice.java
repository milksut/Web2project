package com.storius.storius.controller;

import com.storius.storius.entities.User;
import com.storius.storius.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class controlleradvice
{

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void look_for_user_id(Model model, HttpSession session)
    {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId != null) {
            Optional<User> userOptional = userService.findById(userId);

            // If the user is found, add it to the model
            userOptional.ifPresent(user -> model.addAttribute("user", user));
        }

    }
}
