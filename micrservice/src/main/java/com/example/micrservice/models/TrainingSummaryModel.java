package com.example.micrservice.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSummaryModel {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean trainerStatus;
    private List<YearModel> yearModels;
}
