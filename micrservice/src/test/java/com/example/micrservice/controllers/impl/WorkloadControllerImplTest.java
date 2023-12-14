package com.example.micrservice.controllers.impl;

import com.example.micrservice.clients.AuthServiceClient;
import com.example.micrservice.models.MonthModel;
import com.example.micrservice.models.ResponseAuthorizationModel;
import com.example.micrservice.models.YearModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;
import com.example.micrservice.services.impl.WorkloadServiceImpl;
import com.example.micrservice.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkloadControllerImpl.class)
class WorkloadControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WorkloadServiceImpl workloadService;
    @MockBean
    private AuthServiceClient authServiceClient;

    private String username;
    private String contentType;

    @BeforeEach
    void beforeAll() {
        username = "Ivan.Ivanov";
        contentType = MediaType.APPLICATION_JSON_VALUE;
    }

    @AfterEach
    void afterEach() {
        username = null;
        contentType = null;
    }

    @Test
    @WithMockUser
    void getMonthlySummaryWorkload_withValidData_shouldReturnTrainingSummaryModelList() throws Exception {
        WorkloadModel workloadModel = new WorkloadModel();
        workloadModel.setTrainerFirstName("Ivan");
        workloadModel.setTrainerLastName("Ivanov");
        workloadModel.setTrainerUsername("Ivan.Ivanov");
        workloadModel.setTrainerStatus(true);

        mockAuthorization();
        when(workloadService.getMonthlySummaryWorkload())
                .thenReturn(Collections.singletonList(workloadModel));

        mockMvc.perform(get("/workload/monthly-summary"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[0].trainerFirstName").value(workloadModel.getTrainerFirstName()))
                .andExpect(jsonPath("$.[0].trainerLastName").value(workloadModel.getTrainerLastName()))
                .andExpect(jsonPath("$.[0].trainerUsername").value(workloadModel.getTrainerUsername()))
                .andExpect(jsonPath("$.[0].trainerStatus").value(workloadModel.getTrainerStatus()));

        verify(workloadService)
                .getMonthlySummaryWorkload();
    }

    @Test
    @WithMockUser
    void create_withValidData_shouldReturnWorkloadModel() throws Exception {
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
        workloadModel.setId("1");
        workloadModel.setTrainerFirstName("Ivan");
        workloadModel.setTrainerLastName("Ivanov");
        workloadModel.setTrainerUsername("Ivan.Ivanov");
        workloadModel.setYearModels(yearModels);

        mockAuthorization();
        when(workloadService.create(any(CreateWorkloadModel.class)))
                .thenReturn(workloadModel);

        mockMvc.perform(post("/workload")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(createWorkloadModel))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(workloadModel.getId()))
                .andExpect(jsonPath("$.trainerFirstName").value(workloadModel.getTrainerFirstName()))
                .andExpect(jsonPath("$.trainerLastName").value(workloadModel.getTrainerLastName()))
                .andExpect(jsonPath("$.trainerUsername").value(workloadModel.getTrainerUsername()))
                .andExpect(jsonPath("$.trainerStatus").value(workloadModel.getTrainerStatus()));

        verify(workloadService)
                .create(any(CreateWorkloadModel.class));
    }

    private void mockAuthorization() {
        ResponseAuthorizationModel responseAuthorizationModel = new ResponseAuthorizationModel();
        responseAuthorizationModel.setUsername(username);
        responseAuthorizationModel.setAuthorities("[USER]");

        when(authServiceClient.getAuthorities("token"))
                .thenReturn(responseAuthorizationModel);
    }
}