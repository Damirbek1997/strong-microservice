package com.example.micrservice.models.crud;

import com.example.micrservice.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkloadModel {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean isActive;
    private Date trainingDate;
    private Long trainingDuration;
    private ActionType actionType;
}
