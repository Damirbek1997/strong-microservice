package com.example.micrservice.repositories;

import com.example.micrservice.models.mongo.WorkloadModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkloadRepository extends MongoRepository<WorkloadModel, String> {
    Optional<WorkloadModel> findByTrainerUsername(String username);
    void deleteByTrainerUsername(String username);
}
