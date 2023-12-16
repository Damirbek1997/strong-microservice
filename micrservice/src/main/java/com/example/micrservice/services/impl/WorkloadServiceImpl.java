package com.example.micrservice.services.impl;

import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.YearModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;
import com.example.micrservice.repositories.WorkloadRepository;
import com.example.micrservice.services.WorkloadService;
import com.example.micrservice.services.factory.YearModelFactory;
import com.example.micrservice.utils.DateUtils;
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
    private final YearModelFactory yearModelFactory;
    private final WorkloadMapper workloadMapper;

    @Override
    public List<WorkloadModel> getMonthlySummaryWorkload() {
        List<WorkloadModel> trainingSummaryModels = workloadRepository.findAll();
        log.debug("Get TrainingSummaryModel list, models {}", trainingSummaryModels);
        return trainingSummaryModels;
    }

    @Override
    public WorkloadModel create(CreateWorkloadModel createWorkloadModel) {
        WorkloadModel workloadModel = prepareDocument(createWorkloadModel);
        workloadModel = workloadRepository.save(workloadModel);
        log.debug("Workload was created, model {}", workloadModel);
        return workloadModel;
    }

    @Override
    public void deleteByUsername(String username) {
        workloadRepository.deleteByTrainerUsername(username);
        log.debug("Workload was deleted by username {}", username);
    }

    private WorkloadModel prepareDocument(CreateWorkloadModel createWorkloadModel) {
        Optional<WorkloadModel> modelOptional = workloadRepository.findByTrainerUsername(createWorkloadModel.getTrainerUsername());
        return modelOptional.map(workloadModel -> updatingOldModel(workloadModel, createWorkloadModel))
                .orElseGet(() -> creatingNewModel(createWorkloadModel));

    }

    private WorkloadModel updatingOldModel(WorkloadModel oldModel, CreateWorkloadModel createWorkloadModel) {
        int year = DateUtils.getYear(createWorkloadModel.getTrainingDate());
        int month = DateUtils.getMonth(createWorkloadModel.getTrainingDate());
        boolean isExist = false;

        for (YearModel yearModel : oldModel.getYearModels()) {
            if (yearModel.getYear() == year) {
                isExist = true;
                yearModelFactory.updateYearModel(yearModel, month, createWorkloadModel.getTrainingDuration());
                break;
            }
        }

        if (!isExist) {
            yearModelFactory.updateYearModels(oldModel.getYearModels(), year, month, createWorkloadModel.getTrainingDuration());
        }

        return oldModel;
    }

    private WorkloadModel creatingNewModel(CreateWorkloadModel createWorkloadModel) {
        int year = DateUtils.getYear(createWorkloadModel.getTrainingDate());
        int month = DateUtils.getMonth(createWorkloadModel.getTrainingDate());

        WorkloadModel newModel = workloadMapper.toDocument(createWorkloadModel);
        YearModel yearModel = yearModelFactory.buildNewYearModel(year, month, createWorkloadModel.getTrainingDuration());
        newModel.setYearModels(List.of(yearModel));
        return newModel;
    }
}
