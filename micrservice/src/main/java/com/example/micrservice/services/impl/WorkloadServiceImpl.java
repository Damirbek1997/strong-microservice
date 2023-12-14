package com.example.micrservice.services.impl;

import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;
import com.example.micrservice.repositories.WorkloadRepository;
import com.example.micrservice.services.WorkloadService;
import com.example.micrservice.services.factory.WorkloadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadServiceImpl implements WorkloadService {
    private final WorkloadRepository workloadRepository;
    private final WorkloadFactory workloadFactory;

    @Override
    public List<WorkloadModel> getMonthlySummaryWorkload() {
        List<WorkloadModel> trainingSummaryModels = workloadRepository.findAll();
        log.debug("Get TrainingSummaryModel list, models {}", trainingSummaryModels);
        return trainingSummaryModels;
    }

    @Override
    public WorkloadModel create(CreateWorkloadModel createWorkloadModel) {
        Optional<WorkloadModel> modelOptional = workloadRepository.findByTrainerUsername(createWorkloadModel.getTrainerUsername());
        WorkloadModel workloadModel = workloadFactory.prepareModel(modelOptional, createWorkloadModel);
        workloadModel = workloadRepository.save(workloadModel);
        log.debug("Workload was created, model {}", workloadModel);
        return workloadModel;
    }

    @Override
    public void deleteByUsername(String username) {
        workloadRepository.deleteByTrainerUsername(username);
        log.debug("Workload was deleted by username {}", username);
    }
}
