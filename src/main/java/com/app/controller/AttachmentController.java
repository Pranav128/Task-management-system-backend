package com.app.controller;


import com.app.entity.Attachment;
import com.app.payload.response.AttachmentResponse;
import com.app.payload.response.Response;
import com.app.service.AttachmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping
    public ResponseEntity<List<Attachment>> getAll(@PathVariable Long taskId){
        List<Attachment> attachments=attachmentService.findAll(taskId);
        return ResponseEntity.ok(attachments);
    }

    @PostMapping
    public ResponseEntity<AttachmentResponse> uploadAttachment(
            @PathVariable Long taskId,
            @RequestPart("file") MultipartFile file) {
        AttachmentResponse attachment = attachmentService.uploadAttachment(taskId, file);
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long taskId, @PathVariable Long attachmentId) {
        byte[] fileContent = attachmentService.downloadAttachment(attachmentId);

        // Fetch file metadata (filename, type) from DB (optional)
        String fileName = "attachment-" + attachmentId + ".file";  // Replace with actual file name from DB
        String contentType = "application/octet-stream"; // Replace with actual MIME type

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Response> deleteAttachment(@PathVariable Long taskId, @PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.ok(new Response("✅ Attachment deleted successfully."));
    }
}