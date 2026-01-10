package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.TaskDto;
import com.eazybytes.cards.entity.Task;

public class TaskMapper {

    public static TaskDto mapToTaskDto(Task task, TaskDto dto) {
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setEmail(task.getEmail());
        dto.setExecuteAt(task.getExecuteAt());
        dto.setStatus(task.getStatus());
        dto.setAttempts(task.getAttempts());
//        dto.setCreatedAt(task.getCreatedAt());
//        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setExecutedAt(task.getExecutedAt());
        dto.setLastError(task.getLastError());
        return dto;
    }

    public static Task mapToTask(TaskDto dto, Task task) {
        // don't overwrite id/createdAt automatically unless you want to
        task.setDescription(dto.getDescription());
        task.setEmail(dto.getEmail());
        task.setExecuteAt(dto.getExecuteAt());

        // optional fields
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());
        if (dto.getAttempts() != null) task.setAttempts(dto.getAttempts());
        task.setExecutedAt(dto.getExecutedAt());
        task.setLastError(dto.getLastError());
        return task;
    }
}
