package com.example.micrservice.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearModel {
    private int year;
    private List<MonthModel> monthModels;
}
