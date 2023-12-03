package com.example.micrservice.repositories;

import com.example.micrservice.entities.TrainingSummaryDaoModel;
import com.example.micrservice.models.MonthModel;
import com.example.micrservice.models.TrainingSummaryMainInfoModel;
import com.example.micrservice.models.TrainingSummaryModel;
import com.example.micrservice.models.YearModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class WorkloadJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<TrainingSummaryModel> getTrainingSummaryByMonthAndYear() {
        String sql = "SELECT EXTRACT(MONTH FROM training_date) AS month, EXTRACT(YEAR FROM training_date) AS year, " +
                "trainer_username, trainer_first_name, trainer_last_name, is_active, " +
                "SUM(training_duration) AS total_training_duration " +
                "FROM workloads " +
                "WHERE training_date IS NOT NULL " +
                "GROUP BY EXTRACT(MONTH FROM training_date), EXTRACT(YEAR FROM training_date), " +
                "trainer_username, trainer_first_name, trainer_last_name, is_active";

        List<TrainingSummaryDaoModel> trainingSummaryDaoModels = jdbcTemplate.query(sql, (resultSet, i) -> {
            TrainingSummaryDaoModel summary = new TrainingSummaryDaoModel();
            summary.setTrainerUsername(resultSet.getString("trainer_username"));
            summary.setTrainerFirstName(resultSet.getString("trainer_first_name"));
            summary.setTrainerLastName(resultSet.getString("trainer_last_name"));
            summary.setTrainerStatus(resultSet.getBoolean("is_active"));
            summary.setMonth(resultSet.getInt("month"));
            summary.setYear(resultSet.getInt("year"));
            summary.setTotalDuration(resultSet.getLong("total_training_duration"));
            return summary;
        });

        return toModel(trainingSummaryDaoModels);
    }

    private List<TrainingSummaryModel> toModel(List<TrainingSummaryDaoModel> daoModels) {
        List<TrainingSummaryModel> result = new ArrayList<>();
        Map<TrainingSummaryMainInfoModel, List<YearModel>> mainInfoModelMap = getUniqueInfoAndHisDetails(daoModels);

        for (Map.Entry<TrainingSummaryMainInfoModel, List<YearModel>> entry : mainInfoModelMap.entrySet()) {
            TrainingSummaryMainInfoModel mainInfoModel = entry.getKey();
            result.add(TrainingSummaryModel.builder()
                    .trainerUsername(mainInfoModel.getTrainerUsername())
                    .trainerFirstName(mainInfoModel.getTrainerFirstName())
                    .trainerLastName(mainInfoModel.getTrainerLastName())
                    .trainerStatus(mainInfoModel.getTrainerStatus())
                    .yearModels(entry.getValue())
                    .build());
        }

        return result;
    }

    private Map<TrainingSummaryMainInfoModel, List<YearModel>> getUniqueInfoAndHisDetails(List<TrainingSummaryDaoModel> daoModels) {
        Map<TrainingSummaryMainInfoModel, List<YearModel>> mainInfoModelMap = new HashMap<>();

        for (TrainingSummaryDaoModel daoModel : daoModels) {
            TrainingSummaryMainInfoModel mainInfoModel = buildMainInfoModel(daoModel);
            List<YearModel> yearModels = mainInfoModelMap.get(mainInfoModel);

            if (yearModels == null) {
                MonthModel monthModel = new MonthModel(daoModel.getMonth(), daoModel.getTotalDuration());
                YearModel yearModel = new YearModel(daoModel.getYear(), new ArrayList<>(List.of(monthModel)));
                mainInfoModelMap.put(mainInfoModel, new ArrayList<>(List.of(yearModel)));
            } else {
                addYearsTo(yearModels, daoModel);
                addMonthTo(yearModels, daoModel);
            }
        }

        return mainInfoModelMap;
    }

    private TrainingSummaryMainInfoModel buildMainInfoModel(TrainingSummaryDaoModel daoModel) {
        return TrainingSummaryMainInfoModel.builder()
                .trainerUsername(daoModel.getTrainerUsername())
                .trainerFirstName(daoModel.getTrainerFirstName())
                .trainerLastName(daoModel.getTrainerLastName())
                .trainerStatus(daoModel.getTrainerStatus())
                .build();
    }

    private void addMonthTo(List<YearModel> yearModels, TrainingSummaryDaoModel daoModel) {
        yearModels.stream()
                .filter(yearModel -> yearModel.getYear() == daoModel.getYear())
                .findFirst()
                .ifPresent(yearModel -> yearModel.getMonthModels().add(new MonthModel(daoModel.getMonth(), daoModel.getTotalDuration())));
    }

    private void addYearsTo(List<YearModel> yearModels, TrainingSummaryDaoModel daoModel) {
        boolean yearExistsIn = yearModels.stream()
                .anyMatch(yearModel -> yearModel.getYear() == daoModel.getYear());

        if (!yearExistsIn) {
            yearModels.add(new YearModel(daoModel.getYear(), new ArrayList<>()));
        }
    }
}
