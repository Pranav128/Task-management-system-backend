package com.app.repository;

import com.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserId(Long userId);
    @Query("SELECT n FROM Notification n WHERE n.task.id =?1")
    List<Notification> findByTask(Long taskId);
}
