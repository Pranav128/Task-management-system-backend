package com.app.repository;

import com.app.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findAll(Pageable pageable);
    List<Task> findByCreatedById(Long createdById);

    // ✅ Fetch paginated tasks assigned to a user
    @Query("SELECT t FROM Task t JOIN t.assignees a WHERE a.id = :userId")
    Page<Task> findTasksByAssigneeId(@Param("userId") Long userId, Pageable pageable);

}
