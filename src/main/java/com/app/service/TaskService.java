package com.app.service;

import com.app.entity.Comment;
import com.app.entity.Task;
import com.app.entity.User;
import com.app.exception.custom.TaskException;
import com.app.payload.request.CreateTaskRequest;
import com.app.payload.request.UpdateTaskRequest;
import com.app.payload.response.TaskResponse;
import com.app.repository.CommentRepository;
import com.app.repository.TaskRepository;
import com.app.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final CommentRepository commentRepository;
    private final AttachmentService attachmentService;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, NotificationService notificationService, CommentRepository commentRepository, AttachmentService attachmentService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.commentRepository = commentRepository;
        this.attachmentService = attachmentService;
    }

    public TaskResponse createTask(CreateTaskRequest request, Long createdById, List<Long> assigneeIds, String comments, List<MultipartFile> attachments) {
        User createdBy = userRepository.findById(createdById).orElseThrow();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setCreatedBy(createdBy);

        List<User> assignees = userRepository.findAllById(assigneeIds);
        task.setAssignees(assignees);

        Task savedTask = taskRepository.save(task);

        Comment comment = new Comment();
        comment.setComment(comments);
        comment.setTask(savedTask);
        comment.setUser(createdBy);

        // Save the comment
        Comment savedComment = commentRepository.save(comment);

        //Attachment
        for(MultipartFile i : attachments){
            attachmentService.uploadAttachment(savedTask.getId(),i);
        }

        // Trigger notification
        for(User u:assignees){
            notificationService.createNotification(
                u.getId(),
                savedTask.getId(),
                "You have been assigned a new task: " + savedTask.getTitle()
            );
        }
//        return savedTask;
        return mapToTaskResponse(savedTask);
    }

    public Page<TaskResponse> getAllTasks(int page, int size, String sort) {
        // Parse the sort parameter (e.g., "dueDate,asc")
        String[] sortParams = sort.split(",");
        String sortBy = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        // Create a Pageable object
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Fetch tasks with pagination and sorting
        Page<Task> tasks = taskRepository.findAll(pageable);

        // Map tasks to TaskResponse
        return tasks.map(this::mapToTaskResponse);
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskException("Task not found"));
        return mapToTaskResponse(task);
    }

    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return mapToTaskResponse(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> searchTasks(String title, String priority, String status) {
        // Step 1: Retrieve all tasks from the repository
        List<Task> tasks = taskRepository.findAll();

        // Step 2: Filter by title (if provided)
        if (title != null && !title.isEmpty()) {
            tasks = tasks.stream()
                    .filter(task -> task.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Step 3: Filter by priority (if provided)
        if (priority != null && !priority.isEmpty()) {
            tasks = tasks.stream()
                    .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                    .collect(Collectors.toList());
        }

        // Step 4: Filter by status (if provided)
        if (status != null && !status.isEmpty()) {
            tasks = tasks.stream()
                    .filter(task -> task.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        // Step 5: Convert tasks to TaskResponse objects (if needed)
        return tasks.stream()
                .map(this::mapToTaskResponse)
                .collect(Collectors.toList());

//        return taskResponses;
    }

    private TaskResponse mapToTaskResponse(Task task) {
        if(task.getComments()==null){
            return null;
        }
        List<String> comments = task.getComments().stream()
                .map(Comment::getComment)
                .collect(Collectors.toList());
        List<String> assignees = task.getAssignees().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedBy().getUsername(),
                assignees,
                comments
        );
    }
}

