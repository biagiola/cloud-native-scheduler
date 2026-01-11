package com.biagiola.task.controller;

import com.biagiola.task.constants.TaskConstants;
import com.biagiola.task.dto.ErrorResponseDto;
import com.biagiola.task.dto.ResponseDto;
import com.biagiola.task.dto.TaskDto;
import com.biagiola.task.dto.TaskStatusUpdateDto;
import com.biagiola.task.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "CRUD REST APIs for Tasks",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE task details"
)
@RestController
@RequestMapping(path = "/api/tasks", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class TaskController {

    private final ITaskService taskService;

    @Operation(summary = "Create Task REST API", description = "REST API to create a new Task")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        UUID id = taskService.createTask(taskDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskConstants.STATUS_201, TaskConstants.MESSAGE_201 + id));
    }

    @Operation(summary = "Fetch Task by id REST API", description = "REST API to fetch a Task by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> fetchTask(@PathVariable UUID taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.fetchTask(taskId));
    }

    @Operation(summary = "Fetch Tasks by email REST API", description = "REST API to fetch all Tasks by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<TaskDto>> fetchTasksByEmail(@RequestParam @Email String email) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.fetchTasksByEmail(email));
    }

    @Operation(summary = "Update Task REST API", description = "REST API to update a Task by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDto> updateTask(@PathVariable UUID taskId, @Valid @RequestBody TaskDto taskDto) {
        boolean isUpdated = taskService.updateTask(taskId, taskDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(TaskConstants.STATUS_200, TaskConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_UPDATE));
    }

    @Operation(summary = "Update Task Status REST API", description = "REST API to update a Task status by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{taskId}")
    public ResponseEntity<ResponseDto> updateTaskStatus(@PathVariable UUID taskId,
                                                        @Valid @RequestBody TaskStatusUpdateDto taskStatusUpdateDto) {
        boolean isUpdated = taskService.updateTaskStatus(taskId, taskStatusUpdateDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(TaskConstants.STATUS_200, TaskConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_UPDATE));
    }

    @Operation(summary = "Delete Task REST API", description = "REST API to delete a Task by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDto> deleteTask(@PathVariable UUID taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(TaskConstants.STATUS_200, TaskConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(TaskConstants.STATUS_417, TaskConstants.MESSAGE_417_DELETE));
    }
}

