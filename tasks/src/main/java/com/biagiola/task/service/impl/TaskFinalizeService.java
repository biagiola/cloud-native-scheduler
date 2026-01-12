package com.biagiola.task.service.impl;

import com.biagiola.task.entity.TaskStatus;
import com.biagiola.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class TaskFinalizeService {

    private final TaskRepository taskRepository;

    public TaskFinalizeService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void markSuccess(UUID id) {
        taskRepository.finalizeTask(id, TaskStatus.SUCCESS, Instant.now(), null);
    }

    @Transactional
    public void markFailed(UUID id, String error) {
        taskRepository.finalizeTask(id, TaskStatus.FAILED, Instant.now(), error);
    }
}