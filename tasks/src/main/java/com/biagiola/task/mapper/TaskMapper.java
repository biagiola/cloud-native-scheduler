package com.biagiola.task.mapper;

import com.biagiola.task.dto.TaskDto;
import com.biagiola.task.dto.TaskStatusUpdateDto;
import com.biagiola.task.entity.Task;

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
        dto.setExecuteAt(task.getExecuteAt());
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
        task.setExecuteAt(dto.getExecuteAt());
        task.setLastError(dto.getLastError());
        return task;
    }

    public static Task mapStatusUpdate(TaskStatusUpdateDto dto, Task task) {
        task.setStatus(dto.getStatus());
        if (dto.getExecuteAt() != null) task.setExecuteAt(dto.getExecuteAt());
        if (dto.getLastError() != null) task.setLastError(dto.getLastError());
        return task;
    }
}
