package com.example.micrservice.controllers.impl;

import com.example.micrservice.controllers.WorkloadController;
import com.example.micrservice.models.TrainingSummaryModel;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkloadControllerImpl implements WorkloadController {
    private final WorkloadService workloadService;

    @Override
    public List<TrainingSummaryModel> getMonthlySummaryWorkload() {
        return workloadService.getMonthlySummaryWorkload();
    }

    @Override
    public WorkloadModel create(CreateWorkloadModel createWorkloadModel) {
        return workloadService.create(createWorkloadModel);
    }
}
