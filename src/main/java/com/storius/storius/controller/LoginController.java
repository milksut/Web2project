package com.storius.storius.controller;

import com.storius.storius.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Controller
public class LoginController
{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        if (logout != null) {
            model.addAttribute("logoutSuccess", true);
        }
        return "login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }
}
