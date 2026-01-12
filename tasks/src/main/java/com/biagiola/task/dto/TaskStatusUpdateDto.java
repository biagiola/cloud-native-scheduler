package com.biagiola.task.dto;

import com.biagiola.task.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Schema(name = "TaskStatusUpdate", description = "Schema to update task status and error details")
@Data
public class TaskStatusUpdateDto {

    @NotNull(message = "status can not be null")
    @Schema(description = "Task status", example = "FAILED")
    private TaskStatus status;

    @Schema(description = "Last execution timestamp (UTC)", example = "2026-01-09T12:30:05Z")
    private Instant executeAt;

    @Schema(description = "Last error message if failed", example = "SMTP server unavailable")
    private String lastError;
}