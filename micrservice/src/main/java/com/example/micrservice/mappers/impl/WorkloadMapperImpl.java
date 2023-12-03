package com.example.micrservice.mappers.impl;

import com.example.micrservice.entities.Workload;
import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
        model.setActionType(entity.getActionType());

        return model;
    }

    @Override
    public List<WorkloadModel> toModel(List<Workload> entities) {
        List<WorkloadModel> workloadModels = new ArrayList<>();

        for (Workload entity : entities) {
            workloadModels.add(toModel(entity));
        }

        return workloadModels;
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
        entity.setActionType(model.getActionType());

        return entity;
    }
}
