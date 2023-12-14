package com.example.micrservice.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthModel {
    private int month;
    private Long duration;
}
