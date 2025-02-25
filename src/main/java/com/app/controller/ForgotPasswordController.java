package com.app.controller;

import com.app.payload.response.Response;
import com.app.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    // Generate password reset link
    @PostMapping("/forgot-password")
    public ResponseEntity<Response> forgotPassword(@RequestBody String email) {
        System.out.println(email);
        forgotPasswordService.generateResetToken(email);
        return ResponseEntity.ok(new Response("Password reset link sent!"));
    }

    // Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        boolean success = forgotPasswordService.resetPassword(token, newPassword);
        return success ? ResponseEntity.ok(new Response("Password successfully reset!")) : ResponseEntity.badRequest().body(new Response("Invalid or expired token."));
    }
}
