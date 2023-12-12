package com.example.micrservice.repositories;

import com.example.micrservice.entities.Workload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkloadRepository extends JpaRepository<Workload, Long> {
    void deleteByTrainerUsername(String username);
}
