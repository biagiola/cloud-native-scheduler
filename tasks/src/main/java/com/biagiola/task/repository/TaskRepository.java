package com.biagiola.task.repository;

import com.biagiola.task.entity.Task;
import com.biagiola.task.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByEmail(String email);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByStatusAndExecuteAtLessThanEqual(TaskStatus status, Instant now);
}

