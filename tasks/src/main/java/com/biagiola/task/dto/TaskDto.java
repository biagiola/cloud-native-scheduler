package com.biagiola.task.dto;

import com.biagiola.task.json.SecondsOrInstantDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.biagiola.task.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Schema(name = "Task", description = "Schema to hold Task information")
@Data
public class TaskDto {

    @Schema(description = "Task ID (UUID)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @NotBlank(message = "Description can not be null or empty")
    @Size(max = 500, message = "Description must be at most 500 characters")
    @Schema(description = "Task description", example = "Notify user about payment due")
    private String description;

    @NotBlank(message = "Email can not be null or empty")
    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must be at most 254 characters")
    @Schema(description = "Email to notify", example = "user@example.com")
    private String email;

    @NotNull(message = "executeAt can not be null")
    @JsonDeserialize(using = SecondsOrInstantDeserializer.class)
    @Schema(description = "Execution time in UTC (Instant)", example = "2026-01-09T12:30:00Z")
    private Instant executeAt;

    @Schema(description = "Task status", example = "SCHEDULED")
    public TaskStatus status;

    @Schema(description = "Number of attempts", example = "0")
    private Integer attempts;

    @Schema(description = "Last execution timestamp (UTC)", example = "2026-01-09T12:30:05Z")
    private Instant executedAt;

    @Schema(description = "Last error message if failed", example = "SMTP server unavailable")
    private String lastError;
}
