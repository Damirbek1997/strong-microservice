package com.example.micrservice.models.mongo;

import com.example.micrservice.models.YearModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("workloads")
public class WorkloadModel {
    @Id
    private String id;
    @Field
    private String trainerUsername;
    @Field
    private String trainerFirstName;
    @Field
    private String trainerLastName;
    @Field
    private Boolean trainerStatus;
    @Field
    private List<YearModel> yearModels;
}
