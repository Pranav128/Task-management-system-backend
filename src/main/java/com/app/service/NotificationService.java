package com.app.service;


import com.app.entity.Notification;
import com.app.entity.Task;
import com.app.entity.User;
import com.app.payload.response.NotificationResponse;
import com.app.repository.NotificationRepository;
import com.app.repository.TaskRepository;
import com.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Create a notification
    public NotificationResponse createNotification(Long userId, Long taskId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setTask(task);

        Notification savedNotification = notificationRepository.save(notification);
        return mapToNotificationResponse(savedNotification);
    }

    // Fetch all notifications for a user
    public List<NotificationResponse> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(this::mapToNotificationResponse)
                .collect(Collectors.toList());
    }

    // Mark a notification as read
    public NotificationResponse markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        return mapToNotificationResponse(updatedNotification);
    }

    // Map Notification to NotificationResponse
    private NotificationResponse mapToNotificationResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt(),
                notification.getTask().getId(),
                notification.getUser().getUsername()
        );
    }
}
