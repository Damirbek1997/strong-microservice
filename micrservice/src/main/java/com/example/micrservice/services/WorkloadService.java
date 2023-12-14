package com.example.micrservice.services;

import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;

import java.util.List;

public interface WorkloadService {
    List<WorkloadModel> getMonthlySummaryWorkload();
    WorkloadModel create(CreateWorkloadModel createWorkloadModel);
    void deleteByUsername(String username);
}
