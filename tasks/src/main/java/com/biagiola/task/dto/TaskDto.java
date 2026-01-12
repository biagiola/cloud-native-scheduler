package com.biagiola.task.dto;

import com.biagiola.task.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Schema(name = "Task", description = "Schema to hold Task information")
@Data
public class TaskDto {

    private static final Clock CLOCK = Clock.systemUTC();

    @Schema(description = "Task ID (UUID)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @NotBlank(message = "Description can not be null or empty")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @NotBlank(message = "Email can not be null or empty")
    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must be at most 254 characters")
    private String email;

    @Schema(description = "Task status", example = "PENDING")
    private TaskStatus status;

    @Schema(description = "Number of attempts", example = "0")
    private Integer attempts;

    @NotNull(message = "executeAt can not be null")
    @Schema(
            description = "Either ISO-8601 instant (e.g. 2026-01-10T15:30:00Z) OR seconds from now (e.g. 50)",
            example = "50"
    )
    private Instant executeAt;

    @Schema(description = "Last error message if failed", example = "SMTP server unavailable")
    private String lastError;

    /**
     * Accepts:
     *  - executeAt: 50        (number)
     *  - executeAt: "50"      (string number)
     *  - executeAt: "2026-01-10T15:30:00Z" (ISO instant)
     */
    @JsonSetter("executeAt")
    public void setExecuteAt(Object raw) {
        if (raw == null) {
            this.executeAt = null;
            return;
        }

        // JSON number: executeAt: 50
        if (raw instanceof Number n) {
            long seconds = n.longValue();
            validateSeconds(seconds);
            this.executeAt = Instant.now(CLOCK).plusSeconds(seconds);
            return;
        }

        // JSON string: executeAt: "50" OR "2026-01-10T15:30:00Z"
        if (raw instanceof String s) {
            String value = s.trim();

            if (value.matches("^\\d+$")) {
                long seconds = Long.parseLong(value);
                validateSeconds(seconds);
                this.executeAt = Instant.now(CLOCK).plusSeconds(seconds);
                return;
            }

            try {
                this.executeAt = Instant.parse(value);
                return;
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "executeAt must be ISO-8601 (e.g. 2026-01-10T15:30:00Z) or seconds-from-now (e.g. 50)"
                );
            }
        }

        throw new IllegalArgumentException("executeAt must be a number or a string");
    }

    private void validateSeconds(long seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("executeAt seconds must be >= 0");
        }
    }
}
