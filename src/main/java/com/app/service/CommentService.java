package com.app.service;

import com.app.entity.Comment;
import com.app.entity.Task;
import com.app.entity.User;
import com.app.exception.custom.TaskException;
import com.app.payload.request.CreateCommentRequest;
import com.app.payload.response.CommentResponse;
import com.app.repository.CommentRepository;
import com.app.repository.TaskRepository;
import com.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Add a comment to a task
    public CommentResponse addComment(CreateCommentRequest request,Long taskId) {
        // Fetch the task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskException("Task not found"));

        // Fetch the user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new TaskException("User not found"));

        // Create the comment
        Comment comment = new Comment();
        comment.setComment(request.getComment()+" #"+task.getTitle());
        comment.setTask(task);
        comment.setUser(user);

        // Save the comment
        Comment savedComment = commentRepository.save(comment);

        // Return the response
        return mapToCommentResponse(savedComment);
    }

    // Get all comments for a task
    public List<CommentResponse> getCommentsByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return comments.stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    // Map Comment entity to CommentResponse DTO
    private CommentResponse mapToCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUser().getUsername()
        );
    }
}