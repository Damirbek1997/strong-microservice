package com.example.micrservice.services.impl;

import com.example.micrservice.models.MonthModel;
import com.example.micrservice.models.YearModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;
import com.example.micrservice.repositories.WorkloadRepository;
import com.example.micrservice.services.factory.YearModelFactory;
import com.example.micrservice.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
    YearModelFactory yearModelFactory;

    @Test
    void getMonthlySummaryWorkload_withValidData_shouldReturnTrainingSummaryModelList() {
        WorkloadModel workloadModel = new WorkloadModel();
        workloadModel.setTrainerUsername("Trainer.Trainer");
        workloadModel.setTrainerFirstName("Trainer");
        workloadModel.setTrainerLastName("Trainer");
        workloadModel.setTrainerStatus(true);

        when(workloadRepository.findAll())
                .thenReturn(Collections.singletonList(workloadModel));

        List<WorkloadModel> responses = workloadService.getMonthlySummaryWorkload();

        assertEquals(1, responses.size());
        verify(workloadRepository)
                .findAll();
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

        MonthModel monthModel = new MonthModel();
        monthModel.setMonth(DateUtils.getMonth(date));
        monthModel.setDuration(10L);

        List<MonthModel> monthModels = new ArrayList<>();
        monthModels.add(monthModel);

        YearModel yearModel = new YearModel();
        yearModel.setYear(DateUtils.getYear(date));
        yearModel.setMonthModels(monthModels);

        List<YearModel> yearModels = new ArrayList<>();
        yearModels.add(yearModel);

        WorkloadModel workloadModel = new WorkloadModel();
        workloadModel.setId("1L");
        workloadModel.setTrainerFirstName("Ivan");
        workloadModel.setTrainerLastName("Ivanov");
        workloadModel.setTrainerUsername("Ivan.Ivanov");
        workloadModel.setYearModels(yearModels);

        when(workloadRepository.findByTrainerUsername(createWorkloadModel.getTrainerUsername()))
                .thenReturn(Optional.of(workloadModel));
        when(workloadRepository.save(workloadModel))
                .thenReturn(workloadModel);

        WorkloadModel response = workloadService.create(createWorkloadModel);

        assertEquals(response.getId(), workloadModel.getId());
        assertEquals(response.getTrainerFirstName(), workloadModel.getTrainerFirstName());
        assertEquals(response.getTrainerLastName(), workloadModel.getTrainerLastName());
        assertEquals(response.getTrainerUsername(), workloadModel.getTrainerUsername());
        assertEquals(response.getYearModels(), workloadModel.getYearModels());
    }
}