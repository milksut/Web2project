package com.storius.storius.controller;

import com.storius.storius.entities.User;
import com.storius.storius.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String getRegister(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "success", required = false) String success,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("registerError", error);
        }
        if (success != null) {
            model.addAttribute("registerSuccess", true);
        }
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model
    ) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("registerError", "Passwords do not match");
            return "register";
        }

        if (userService.findByEmail(email).isPresent()) {
            model.addAttribute("registerError", "Email already registered");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.save(user);

        return "redirect:/register?success";
    }
}
