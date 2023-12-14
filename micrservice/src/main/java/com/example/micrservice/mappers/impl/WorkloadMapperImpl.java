package com.example.micrservice.mappers.impl;

import com.example.micrservice.exceptions.BadRequestException;
import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkloadMapperImpl implements WorkloadMapper {
    @Override
    public WorkloadModel toDocument(CreateWorkloadModel model) {
        validateCreateWorkloadModel(model);
        return WorkloadModel.builder()
                .trainerUsername(model.getTrainerUsername())
                .trainerFirstName(model.getTrainerFirstName())
                .trainerLastName(model.getTrainerLastName())
                .trainerStatus(model.getIsActive())
                .build();
    }

    private void validateCreateWorkloadModel(CreateWorkloadModel model) {
        if (model.getTrainerUsername() == null) {
            throw new BadRequestException("Field trainerUsernameMust be filled!");
        }

        if (model.getTrainerFirstName() == null) {
            throw new BadRequestException("Field trainerFirstName must be filled!");
        }

        if (model.getTrainerLastName() == null) {
            throw new BadRequestException("Field trainerLastName must be filled!");
        }

        if (model.getIsActive() == null) {
            throw new BadRequestException("Field isActive must be filled!");
        }

        if (model.getTrainingDate() == null) {
            throw new BadRequestException("Field trainingDate must be filled!");
        }

        if (model.getTrainingDuration() == null) {
            throw new BadRequestException("Field trainingDuration must be filled!");
        }
    }
}
