package com.example.micrservice.entities;

import com.example.micrservice.enums.ActionType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workloads")
public class Workload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "trainer_username")
    private String trainerUsername;

    @Column(name = "trainer_first_name")
    private String trainerFirstName;

    @Column(name = "trainer_last_name")
    private String trainerLastName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "training_date")
    private Date trainingDate;

    @Column(name = "training_duration")
    private Long trainingDuration;

    @Column(name = "action_type")
    @Enumerated(value = EnumType.STRING)
    private ActionType actionType;
}
