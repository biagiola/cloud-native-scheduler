package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.TaskDto;
import com.eazybytes.cards.dto.TaskStatusUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ITaskService {

    UUID createTask(TaskDto taskDto);

    TaskDto fetchTask(UUID taskId);

    List<TaskDto> fetchTasksByEmail(String email);

    boolean updateTask(UUID taskId, TaskDto taskDto);

    boolean updateTaskStatus(UUID taskId, TaskStatusUpdateDto taskStatusUpdateDto);

    boolean deleteTask(UUID taskId);
}
