package com.app.controller;

import com.app.payload.request.CreateCommentRequest;
import com.app.payload.response.CommentResponse;
import com.app.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Add a comment to a task
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long taskId,
            @RequestBody CreateCommentRequest request) {
        CommentResponse comment = commentService.addComment(request,taskId);
        return ResponseEntity.ok(comment);
    }

    // Get all comments for a task
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByTaskId(@PathVariable Long taskId) {
        List<CommentResponse> comments = commentService.getCommentsByTaskId(taskId);
        return ResponseEntity.ok(comments);
    }
}
