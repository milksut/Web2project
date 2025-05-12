package com.storius.storius.controller;

import com.storius.storius.inceptor.redirect_login;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController
{
    private static final Logger logger = LoggerFactory.getLogger(redirect_login.class);

    @GetMapping("/login")
    String getlogin(Model model)
    {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,@RequestParam String password, HttpSession session) {

        //Todo: find user
        session.setAttribute("user_id", 2L);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }
}
