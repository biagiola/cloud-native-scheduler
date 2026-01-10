package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.TaskDto;

import java.util.List;
import java.util.UUID;

public interface ITaskService {

    UUID createTask(TaskDto taskDto);

    TaskDto fetchTask(UUID taskId);

    List<TaskDto> fetchTasksByEmail(String email);

    boolean updateTask(UUID taskId, TaskDto taskDto);

    boolean deleteTask(UUID taskId);
}
