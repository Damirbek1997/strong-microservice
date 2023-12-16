package com.example.micrservice.services.factory;

import com.example.micrservice.models.MonthModel;
import com.example.micrservice.models.YearModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class YearModelFactory {
    public void updateYearModel(YearModel yearModel, int month, Long trainingDuration) {
        MonthModel monthModel = buildMonthModel(month, trainingDuration);
        yearModel.getMonthModels().add(monthModel);
    }

    public void updateYearModels(List<YearModel> yearModels, int year, int month, Long trainingDuration) {
        MonthModel monthModel = buildMonthModel(month, trainingDuration);
        yearModels.add(YearModel.builder()
                .year(year)
                .monthModels(List.of(monthModel))
                .build());
    }

    public YearModel buildNewYearModel(int year, int month, Long trainingDuration) {
        MonthModel monthModel = buildMonthModel(month, trainingDuration);
        return YearModel.builder()
                .year(year)
                .monthModels(List.of(monthModel))
                .build();
    }

    private MonthModel buildMonthModel(int month, Long trainingDuration) {
        return MonthModel.builder()
                .month(month)
                .duration(trainingDuration)
                .build();
    }
}
