package com.example.micrservice.services.impl;

import com.example.micrservice.entities.Workload;
import com.example.micrservice.enums.ActionType;
import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.TrainingSummaryModel;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.repositories.WorkloadJdbcRepository;
import com.example.micrservice.repositories.WorkloadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkloadServiceImplTest {
    @InjectMocks
    private WorkloadServiceImpl workloadService;
    @Mock
    WorkloadRepository workloadRepository;
    @Mock
    WorkloadJdbcRepository workloadJdbcRepository;
    @Mock
    WorkloadMapper workloadMapper;

    @Test
    void getMonthlySummaryWorkload_withValidData_shouldReturnTrainingSummaryModelList() {
        TrainingSummaryModel trainingSummaryModel = new TrainingSummaryModel();
        trainingSummaryModel.setTrainerUsername("Trainer.Trainer");
        trainingSummaryModel.setTrainerFirstName("Trainer");
        trainingSummaryModel.setTrainerLastName("Trainer");
        trainingSummaryModel.setTrainerStatus(true);

        when(workloadJdbcRepository.getTrainingSummaryByMonthAndYear())
                .thenReturn(Collections.singletonList(trainingSummaryModel));

        List<TrainingSummaryModel> responses = workloadService.getMonthlySummaryWorkload();

        assertEquals(1, responses.size());
        verify(workloadJdbcRepository)
                .getTrainingSummaryByMonthAndYear();
    }

    @Test
    void create_withValidData_shouldReturnWorkloadModel() {
        Date date = new Date();

        CreateWorkloadModel createWorkloadModel = new CreateWorkloadModel();
        createWorkloadModel.setTrainerFirstName("Ivan");
        createWorkloadModel.setTrainerLastName("Ivanov");
        createWorkloadModel.setTrainerUsername("Ivan.Ivanov");
        createWorkloadModel.setIsActive(true);
        createWorkloadModel.setTrainingDuration(10L);
        createWorkloadModel.setTrainingDate(date);
        createWorkloadModel.setActionType(ActionType.ADD);

        Workload workload = new Workload();
        workload.setId(1L);
        workload.setTrainerFirstName("Ivan");
        workload.setTrainerLastName("Ivanov");
        workload.setTrainerUsername("Ivan.Ivanov");
        workload.setIsActive(true);
        workload.setTrainingDuration(10L);
        workload.setTrainingDate(date);
        workload.setActionType(ActionType.ADD);

        WorkloadModel workloadModel = new WorkloadModel();
        workloadModel.setId(1L);
        workloadModel.setTrainerFirstName("Ivan");
        workloadModel.setTrainerLastName("Ivanov");
        workloadModel.setTrainerUsername("Ivan.Ivanov");
        workloadModel.setIsActive(true);
        workloadModel.setTrainingDuration(10L);
        workloadModel.setTrainingDate(date);
        workloadModel.setActionType(ActionType.ADD);

        when(workloadMapper.toEntity(createWorkloadModel))
                .thenReturn(workload);
        when(workloadRepository.save(workload))
                .thenReturn(workload);
        when(workloadMapper.toModel(workload))
                .thenReturn(workloadModel);

        WorkloadModel response = workloadService.create(createWorkloadModel);
        assertEquals(workloadModel.getId(), workload.getId());
        assertEquals(workloadModel.getTrainerFirstName(), workload.getTrainerFirstName());
        assertEquals(workloadModel.getTrainerLastName(), workload.getTrainerLastName());
        assertEquals(workloadModel.getTrainerUsername(), workload.getTrainerUsername());
        assertEquals(workloadModel.getIsActive(), workload.getIsActive());
        assertEquals(workloadModel.getActionType(), workload.getActionType());
        assertEquals(workloadModel.getTrainingDate(), workload.getTrainingDate());
        assertEquals(workloadModel.getTrainingDuration(), workload.getTrainingDuration());
    }
}