package com.example.micrservice.mappers.impl;

import com.example.micrservice.entities.Workload;
import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkloadMapperImpl implements WorkloadMapper {
    @Override
    public WorkloadModel toModel(Workload entity) {
        WorkloadModel model = new WorkloadModel();
        model.setId(entity.getId());
        model.setTrainerUsername(entity.getTrainerUsername());
        model.setTrainerFirstName(entity.getTrainerFirstName());
        model.setTrainerLastName(entity.getTrainerLastName());
        model.setIsActive(entity.getIsActive());
        model.setTrainingDate(entity.getTrainingDate());
        model.setTrainingDuration(entity.getTrainingDuration());

        return model;
    }

    @Override
    public List<WorkloadModel> toModel(List<Workload> entities) {
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Workload toEntity(CreateWorkloadModel model) {
        Workload entity = new Workload();
        entity.setTrainerUsername(model.getTrainerUsername());
        entity.setTrainerFirstName(model.getTrainerFirstName());
        entity.setTrainerLastName(model.getTrainerLastName());
        entity.setIsActive(model.getIsActive());
        entity.setTrainingDate(model.getTrainingDate());
        entity.setTrainingDuration(model.getTrainingDuration());

        return entity;
    }
}
