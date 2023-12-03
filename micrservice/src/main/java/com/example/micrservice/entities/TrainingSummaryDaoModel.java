package com.example.micrservice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingSummaryDaoModel {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean trainerStatus;
    private int month;
    private int year;
    private long totalDuration;
}
