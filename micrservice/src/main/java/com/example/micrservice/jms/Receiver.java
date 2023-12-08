package com.example.micrservice.jms;

import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final WorkloadService workloadService;
    private final WorkloadMapper workloadMapper;

    @JmsListener(destination = "workload-create")
    public void receiveWorkload(String json) {
        workloadService.create(workloadMapper.toCreateModel(json));
    }

    @JmsListener(destination = "workload-create-list")
    public void receiveWorkloads(String json) {
        workloadService.create(workloadMapper.toCreateModels(json));
    }
}
