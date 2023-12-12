package com.example.micrservice.services;

import com.example.micrservice.models.TrainingSummaryModel;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;

import java.util.List;

public interface WorkloadService {
    List<TrainingSummaryModel> getMonthlySummaryWorkload();
    WorkloadModel create(CreateWorkloadModel createWorkloadModel);
    void deleteByUsername(String username);
}
