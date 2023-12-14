package com.example.micrservice.mappers;

import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;

public interface WorkloadMapper {
    WorkloadModel toDocument(CreateWorkloadModel model);
}
