package com.biagiola.task.repository;

import com.biagiola.task.entity.Task;
import com.biagiola.task.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByEmail(String email);

    List<Task> findByStatus(TaskStatus status);

    @Query(
            value = """
                SELECT *
                FROM task
                WHERE status = 'PENDING' AND execute_at <= :now
                ORDER BY execute_at ASC
                LIMIT :batchSize
                FOR UPDATE SKIP LOCKED
                """,
            nativeQuery = true
    )
    List<Task> lockDuePendingTasks(@Param("now") Instant now, @Param("batchSize") int batchSize);

    @Modifying
    @Query("""
        UPDATE Task t
           SET t.status = :status,
               t.executeAt = :executeAt,
               t.lastError = :lastError
         WHERE t.id = :id
    """)
    int finalizeTask(
            @Param("id") UUID id,
            @Param("status") TaskStatus status,
            @Param("executeAt") Instant executeAt,
            @Param("lastError") String lastError
    );
}

