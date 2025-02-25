package com.app.service;

import com.app.entity.Attachment;
import com.app.entity.Task;
import com.app.exception.custom.TaskException;
import com.app.payload.response.AttachmentResponse;
import com.app.repository.AttachmentRepository;
import com.app.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final Path rootLocation = Paths.get("uploads"); // Directory to store files

    public AttachmentService(AttachmentRepository attachmentRepository, TaskRepository taskRepository) {
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
    }

    public AttachmentResponse uploadAttachment(Long taskId, MultipartFile file) {
        try {
            // Ensure the upload directory exists
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            // Fetch the task
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskException("Task not found"));

            // Generate a unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Save the file to the upload directory
            Path filePath = rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Save attachment metadata to the database
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setFilePath(filePath.toString());
            attachment.setTask(task);

            Attachment savedAttachment = attachmentRepository.save(attachment);

            // Return the response
            return new AttachmentResponse(
                    savedAttachment.getId(),
                    savedAttachment.getFileName(),
                    savedAttachment.getFileType(),
                    savedAttachment.getFilePath()
            );
        } catch (IOException e) {
            throw new TaskException("Failed to store file: " + e.getMessage());
        }
    }

    public byte[] downloadAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new TaskException("Attachment not found"));
        try {
            Path filePath = Paths.get(attachment.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new TaskException("Failed to read file: " + e.getMessage());
        }
    }

    public List<Attachment> findAll(Long taskId) {
        Optional<Task> t=taskRepository.findById(taskId);
        if(t.isEmpty()){
            throw new TaskException("No task found");
        }
        Task task=t.get();
        if(task.getAttachments() == null){
            throw new TaskException("No attachments found");
        }
        return task.getAttachments();
    }

    public void deleteAttachment(Long attachmentId) {
        // ✅ Find attachment by ID
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new TaskException("Attachment not found"));

        try {
            // ✅ Delete file from storage
            Path filePath = Paths.get(attachment.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new TaskException("Failed to delete file: " + e.getMessage());
        }

        // ✅ Remove attachment from the database
        attachmentRepository.delete(attachment);
    }
}