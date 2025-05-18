package com.storius.storius.controller;

import com.storius.storius.entities.User;
import com.storius.storius.inceptor.redirect_login;
import com.storius.storius.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.mapping.GeneratorCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

@Controller
public class LoginController
{
    private static final Logger logger = LoggerFactory.getLogger(redirect_login.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getlogin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent())
        {
            User user =userOptional.get();
            String hashedInput = hashPassword(password);

            if (hashedInput.equals(user.getPassword()))
            {
                session.setAttribute("user_id", user.getId());
                return "redirect:/";
            }
        }
        
        return "redirect:/login?error";
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegister(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "success", required = false) String success,
                              Model model) {
        if (error != null) {
            model.addAttribute("registerError", error);
        }
        if (success != null) {
            model.addAttribute("registerSuccess", true);
        }
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword,
                                 Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("registerError", "Passwords do not match.");
            return "register";
        }
        if (userService.findByEmail(email).isPresent()) {
            model.addAttribute("registerError", "Email already registered.");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashPassword(password));
        userService.save(user);
        model.addAttribute("registerSuccess", true);
        return "register";
    }
}
