package com.example.micrservice.services.impl;

import com.example.micrservice.entities.Workload;
import com.example.micrservice.exceptions.BadRequestException;
import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.TrainingSummaryModel;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.repositories.WorkloadJdbcRepository;
import com.example.micrservice.repositories.WorkloadRepository;
import com.example.micrservice.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadServiceImpl implements WorkloadService {
    private final WorkloadRepository workloadRepository;
    private final WorkloadJdbcRepository workloadJdbcRepository;
    private final WorkloadMapper workloadMapper;

    @Override
    public List<TrainingSummaryModel> getMonthlySummaryWorkload() {
        List<TrainingSummaryModel> trainingSummaryModels = workloadJdbcRepository.getTrainingSummaryByMonthAndYear();
        log.debug("Get TrainingSummaryModel list, models {}", trainingSummaryModels);
        return trainingSummaryModels;
    }

    @Override
    public WorkloadModel create(CreateWorkloadModel createWorkloadModel) {
        validateCreateWorkloadModel(createWorkloadModel);
        Workload workload = workloadMapper.toEntity(createWorkloadModel);
        WorkloadModel workloadModel = workloadMapper.toModel(workloadRepository.save(workload));
        log.debug("Workload was created, model {}", workloadModel);
        return workloadModel;
    }

    @Override
    public void deleteByUsername(String username) {
        workloadRepository.deleteByTrainerUsername(username);
        log.debug("Workload was deleted by username {}", username);
    }

    private void validateCreateWorkloadModel(CreateWorkloadModel createWorkloadModel) {
        if (createWorkloadModel.getTrainerUsername() == null) {
            throw new BadRequestException("Field trainerUsernameMust be filled!");
        }

        if (createWorkloadModel.getTrainerFirstName() == null) {
            throw new BadRequestException("Field trainerFirstName must be filled!");
        }

        if (createWorkloadModel.getTrainerLastName() == null) {
            throw new BadRequestException("Field trainerLastName must be filled!");
        }

        if (createWorkloadModel.getIsActive() == null) {
            throw new BadRequestException("Field isActive must be filled!");
        }

        if (createWorkloadModel.getTrainingDate() == null) {
            throw new BadRequestException("Field trainingDate must be filled!");
        }

        if (createWorkloadModel.getTrainingDuration() == null) {
            throw new BadRequestException("Field trainingDuration must be filled!");
        }
    }
}
