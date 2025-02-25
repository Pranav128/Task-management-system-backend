package com.app.service;

import com.app.entity.User;
import com.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // Service to send emails

    public ForgotPasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Generate reset token and send email
    public void generateResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30)); // Token valid for 30 minus
            userRepository.save(user);

            // Send reset link via email
//            String resetLink = "http://localhost:4200/reset?token=" + token;
            emailService.sendEmail(user.getEmail(), "Password Reset Request", "Use this token to reset your password: " + token+"\nToken will be expired after 30 minutes");
        }
    }

    // Reset password using token
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Check if token is expired
            if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
                return false; // Token expired
            }

            user.setPasswordHash(passwordEncoder.encode(newPassword)); // Hash password before saving in real apps
            user.setResetToken(null); // Clear token after successful reset
            user.setResetTokenExpiry(null);
            userRepository.save(user);
            emailService.sendEmail(user.getEmail(), "Password Reset Successfully", "User password has been successfully reset");
            return true;
        }
        return false;
    }
}
