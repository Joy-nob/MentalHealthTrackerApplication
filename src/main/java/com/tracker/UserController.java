package com.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Handle user registration
    @PostMapping("/signup")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               Model model) {
        try {
            // Register the user via the service layer
            userService.registerUser(username, password, email);
            return "redirect:/login"; // Redirect to login after successful registration
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; // Stay on signup page if registration fails
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "signup";
        }
    }

    // Handle user login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        try {
            // Authenticate user using Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // If authentication succeeds, create a session for the user (handled by Spring Security)
            if (authentication.isAuthenticated()) {
                return "redirect:/home"; // Redirect to the user's homepage after successful login
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Stay on login page if authentication fails
        }
        model.addAttribute("error", "An unexpected error occurred. Please try again.");
        return "login";
    }

    // DTO for Signup Request
    public static class SignupRequest {
        private String username;
        private String password;
        private String email;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    // DTO for Login Request
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
