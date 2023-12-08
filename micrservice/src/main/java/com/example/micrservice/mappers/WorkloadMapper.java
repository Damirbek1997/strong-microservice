package com.example.micrservice.mappers;

import com.example.micrservice.entities.Workload;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;

import java.util.List;

public interface WorkloadMapper {
    WorkloadModel toModel(Workload entity);
    List<WorkloadModel> toModel(List<Workload> entities);
    Workload toEntity(CreateWorkloadModel model);
}
