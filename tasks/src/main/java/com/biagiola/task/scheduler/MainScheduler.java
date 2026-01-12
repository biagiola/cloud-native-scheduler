package com.biagiola.task.scheduler;

import com.biagiola.task.entity.Task;
import com.biagiola.task.service.impl.TaskClaimService;
import com.biagiola.task.service.impl.TaskFinalizeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainScheduler {

    private final TaskClaimService taskClaimService;
    private final TaskFinalizeService finalizeService;

    public MainScheduler(TaskClaimService taskClaimService, TaskFinalizeService finalizeService) {
        this.taskClaimService = taskClaimService;
        this.finalizeService = finalizeService;
    }

    @Scheduled(fixedRate = 1000)
    public void processPendingTasks() {

        int batchSize = 50; // tune for your load
        List<Task> claimed = taskClaimService.claimDueTasks(batchSize);

        for (Task task : claimed) {
            try {
                // --- DO WORK HERE ---
                // e.g. send email, call external API, etc.
                System.out.println("THIS IS ONE CLAIMED TASK: " + task.getId());

                finalizeService.markSuccess(task.getId());
            } catch (Exception ex) {
                finalizeService.markFailed(task.getId(), ex.getMessage());
            }
        }
    }
}
