package com.app.controller;

import com.app.payload.request.TaskRequest;
import com.app.payload.request.UpdateTaskRequest;
import com.app.payload.response.TaskResponse;
import com.app.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<TaskResponse> createTask(
//            @RequestPart("createTaskRequest") CreateTaskRequest createTaskRequest,
//            @RequestPart("createdById") Long createdById,
//            @RequestPart("assigneeIds") List<Long> assigneeIds,
//            @RequestPart(value = "comments", required = false) String comments,
//            @RequestPart(value = "attachments", required = false) MultipartFile[] attachments) {
//
//        TaskResponse task = taskService.createTask(
//                createTaskRequest,
//                createdById,
//                assigneeIds,
//                comments,
//                attachments
//        );
//        return ResponseEntity.ok(task);
//    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskResponse> createTask(
            @ModelAttribute TaskRequest taskRequest,
            @RequestPart("attachments") List<MultipartFile> attachments
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
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dueDate,asc") String sort) {
        Page<TaskResponse> tasks = taskService.getAllTasks(page, size, sort);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        TaskResponse task = taskService.updateTask(id, request);
        return ResponseEntity.ok(task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
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
//
//    @PostMapping("/{taskId}/attachments")
//    public ResponseEntity<AttachmentResponse> uploadAttachment(
//            @PathVariable Long taskId,
//            @RequestParam("file") MultipartFile file) {
//        AttachmentResponse attachment = attachmentService.uploadAttachment(taskId, file);
//        return ResponseEntity.ok(attachment);
//    }
}
