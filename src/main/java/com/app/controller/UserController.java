package com.app.controller;

import com.app.payload.request.UpdateProfileRequest;
import com.app.payload.response.CommentResponse;
import com.app.payload.response.UserResponseDto;
import com.app.service.CommentService;
import com.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;

    public UserController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    // Get user by ID (including avatar URL)
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String username) {
        UserResponseDto responseDto = userService.getUserById(username);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return userService.findAll();
    }
    @PutMapping("/{username}/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @PathVariable String  username,
            @RequestBody UpdateProfileRequest request) {
        UserResponseDto updatedUser = userService.updateProfile(username, request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}/comments")
    public ResponseEntity<List<CommentResponse>>findCommentsByUserId(@PathVariable Long userId){
        List<CommentResponse> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }
}