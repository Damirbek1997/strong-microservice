package com.example.micrservice.jms;

import com.example.micrservice.enums.Topics;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final WorkloadService workloadService;

    @JmsListener(destination = Topics.CREATE_WORKLOAD_QUEUE_NAME)
    public void create(CreateWorkloadModel createWorkloadModel) {
        workloadService.create(createWorkloadModel);
    }

    @JmsListener(destination = Topics.DELETE_WORKLOAD_QUEUE_NAME)
    public void delete(String trainerUsername) {
        workloadService.deleteByUsername(trainerUsername);
    }
}
