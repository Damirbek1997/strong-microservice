package com.example.micrservice.services.factory;

import com.example.micrservice.mappers.WorkloadMapper;
import com.example.micrservice.models.MonthModel;
import com.example.micrservice.models.YearModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import com.example.micrservice.models.mongo.WorkloadModel;
import com.example.micrservice.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WorkloadFactory {
    private final WorkloadMapper workloadMapper;

    public WorkloadModel prepareModel(Optional<WorkloadModel> modelOptional, CreateWorkloadModel createWorkloadModel) {
        return modelOptional.map(workloadModel -> prepareWorkloadModelIfOldOneIsExist(workloadModel, createWorkloadModel))
                .orElseGet(() -> prepareWorkloadModelIfOldOneIsAbsent(createWorkloadModel));
    }

    private WorkloadModel prepareWorkloadModelIfOldOneIsExist(WorkloadModel oldModel, CreateWorkloadModel createWorkloadModel) {
        List<YearModel> yearModels = prepareList(oldModel, createWorkloadModel);
        WorkloadModel newModel = workloadMapper.toDocument(createWorkloadModel);
        newModel.setYearModels(yearModels);
        return oldModel;
    }

    private WorkloadModel prepareWorkloadModelIfOldOneIsAbsent(CreateWorkloadModel createWorkloadModel) {
        int year = DateUtils.getYear(createWorkloadModel.getTrainingDate());
        int month = DateUtils.getMonth(createWorkloadModel.getTrainingDate());

        List<YearModel> yearModels = new ArrayList<>();
        yearModels.add(prepareNewYear(year, month, createWorkloadModel.getTrainingDuration()));

        WorkloadModel newModel = workloadMapper.toDocument(createWorkloadModel);
        newModel.setYearModels(yearModels);
        return newModel;
    }

    private List<YearModel> prepareList(WorkloadModel oldModel, CreateWorkloadModel createWorkloadModel) {
        int year = DateUtils.getYear(createWorkloadModel.getTrainingDate());
        int month = DateUtils.getMonth(createWorkloadModel.getTrainingDate());

        List<YearModel> yearModels = oldModel.getYearModels();
        yearModels.stream()
                .filter(yearModel -> yearModel.getYear() == year)
                .findFirst()
                .ifPresentOrElse(yearModel -> yearModel.getMonthModels().add(prepareNewMonth(month, createWorkloadModel.getTrainingDuration())),
                        () -> oldModel.getYearModels().add(prepareNewYear(year, month, createWorkloadModel.getTrainingDuration())));

        return yearModels;
    }

    private YearModel prepareNewYear(int year, int month, Long trainingDuration) {
        List<MonthModel> monthModels = new ArrayList<>();
        monthModels.add(prepareNewMonth(month, trainingDuration));
        return YearModel.builder()
                .year(year)
                .monthModels(monthModels)
                .build();
    }

    private MonthModel prepareNewMonth(int month, Long trainingDuration) {
        return MonthModel.builder()
                .month(month)
                .duration(trainingDuration)
                .build();
    }
}
