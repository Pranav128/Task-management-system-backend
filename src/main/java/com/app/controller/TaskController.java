package com.app.controller;

import com.app.payload.request.TaskRequest;
import com.app.payload.request.UpdateTaskRequest;
import com.app.payload.response.Response;
import com.app.payload.response.TaskResponse;
import com.app.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.ALL_VALUE)
    public ResponseEntity<TaskResponse> createTask(
            @ModelAttribute TaskRequest taskRequest,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
    ) {
        // Call the service method
        TaskResponse task = taskService.createTask(
                taskRequest.getCreateTaskRequest(),
                taskRequest.getCreatedById(),
                taskRequest.getAssigneeIds(),
                taskRequest.getComments(),
                attachments
        );
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "dueDate,asc") String sort) {
        Page<TaskResponse> tasks = taskService.getAllTasks(page, size, sort);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }


    // ✅ Fetch paginated tasks assigned to a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TaskResponse>> getTasksByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "dueDate,asc") String sort
    ) {
        Page<TaskResponse> tasks = taskService.getTasksByUserId(userId, page, size, sort);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        TaskResponse task = taskService.updateTask(id, request);
        return ResponseEntity.ok(task);
    }

//    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new Response("Task Deleted Successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String status) {
        List<TaskResponse> tasks = taskService.searchTasks(title, priority, status);
        return ResponseEntity.ok(tasks);
    }

//    @PostMapping("/{taskId}/notify")
//    public ResponseEntity<Void> notifyAssignee(@PathVariable Long taskId) {
//        notificationService.notifyAssignee(taskId);
//        return ResponseEntity.ok().build();
//    }
}
