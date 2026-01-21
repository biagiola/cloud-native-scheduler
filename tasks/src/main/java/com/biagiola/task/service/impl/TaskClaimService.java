package com.biagiola.task.service.impl;

import com.biagiola.task.entity.Task;
import com.biagiola.task.entity.TaskStatus;
import com.biagiola.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TaskClaimService {

    private final TaskRepository taskRepository;

    public TaskClaimService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<Task> claimDueTasks(int batchSize) {
        Instant now = Instant.now(); // UTC

        List<Task> tasks = taskRepository.lockDuePendingTasks(now, batchSize);

        for (Task task : tasks) {
            System.out.println("THIS IS A TASK READY TO PROCESS " + task.getId());
            task.setStatus(TaskStatus.PROCESSING);
            task.setAttempts(task.getAttempts() == null ? 1 : task.getAttempts() + 1);
        }

        // Persist the status change while locks are held, then commit quickly
        taskRepository.saveAll(tasks);

        return tasks;
    }
}
