package com.example.micrservice.jms;

import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final WorkloadService workloadService;

    @JmsListener(destination = "workload-create")
    public void receiveWorkload(CreateWorkloadModel createWorkloadModel) {
        workloadService.create(createWorkloadModel);
    }
}
