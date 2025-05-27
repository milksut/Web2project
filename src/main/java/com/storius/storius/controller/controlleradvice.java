package com.storius.storius.controller;

import com.storius.storius.entities.User;
import com.storius.storius.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@ControllerAdvice
public class controlleradvice
{

    @Autowired
    private UserService userService;

    private final List<String> excludedPaths = List.of("/login", "/register");

    @ModelAttribute
    public void look_for_user_id(Model model, HttpServletRequest request, HttpSession session)
    {
        String path = request.getRequestURI();

        if (excludedPaths.contains(path)) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
           User user = userService.findByEmail(authentication.getName()).get();
           model.addAttribute("user", user);
           session.setAttribute("user_id",user.getId());
        }

    }
}
