package com.storius.storius.services;


import com.storius.storius.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Trying to load user with email: " + email);
        User user = userService.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User not found with email: " + email);
                    return new UsernameNotFoundException("User not found");
                });
        System.out.println("Found user: " + user.getEmail());
        System.out.println("Stored password: " + user.getPassword());
        return user;
    }
}