package com.example.micrservice.models.crud;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("trainerUsername")
    private String trainerUsername;
    @JsonProperty("trainerFirstName")
    private String trainerFirstName;
    @JsonProperty("trainerLastName")
    private String trainerLastName;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("trainingDate")
    private Date trainingDate;
    @JsonProperty("trainingDuration")
    private Long trainingDuration;
}
