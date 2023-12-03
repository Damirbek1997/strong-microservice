package com.example.micrservice.models;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class TrainingSummaryMainInfoModel {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean trainerStatus;
}
