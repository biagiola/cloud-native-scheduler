package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.dto.TaskDto;
import com.eazybytes.cards.dto.TaskStatusUpdateDto;
import com.eazybytes.cards.entity.Task;
import com.eazybytes.cards.entity.TaskStatus;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.TaskMapper;
import com.eazybytes.cards.repository.TaskRepository;
import com.eazybytes.cards.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;

    @Override
    public UUID createTask(TaskDto taskDto) {
        Task task = new Task();
        TaskMapper.mapToTask(taskDto, task);

        // minimal defaults
        if (task.getStatus() == null) task.setStatus(TaskStatus.SCHEDULED);
        if (task.getAttempts() == null) task.setAttempts(0);

        Task saved = taskRepository.save(task);
        return saved.getId();
    }

    @Override
    public TaskDto fetchTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));
        return TaskMapper.mapToTaskDto(task, new TaskDto());
    }

    @Override
    public List<TaskDto> fetchTasksByEmail(String email) {
        return taskRepository.findByEmail(email)
                .stream()
                .map(t -> TaskMapper.mapToTaskDto(t, new TaskDto()))
                .toList();
    }

    @Override
    public boolean updateTask(UUID taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        if (taskDto.getDescription() == null) taskDto.setDescription(task.getDescription());
        if (taskDto.getEmail() == null) taskDto.setEmail(task.getEmail());
        if (taskDto.getExecuteAt() == null) taskDto.setExecuteAt(Instant.now());

        TaskMapper.mapToTask(taskDto, task);
        taskRepository.save(task);
        return true;
    }

    @Override
    public boolean updateTaskStatus(UUID taskId, TaskStatusUpdateDto taskStatusUpdateDto) {
        System.out.println("TaskId: " + taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        if (taskStatusUpdateDto.getStatus() != null
                && taskStatusUpdateDto.getStatus() != task.getStatus()) {
            Integer attempts = task.getAttempts();
            task.setAttempts(attempts == null ? 1 : attempts + 1);
        }

        TaskMapper.mapStatusUpdate(taskStatusUpdateDto, task);
        taskRepository.save(task);

        return true;
    }


    @Override
    public boolean deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));
        taskRepository.delete(task);
        return true;
    }
}
