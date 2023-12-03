package com.example.micrservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthModel {
    private int month;
    private Long duration;
}
