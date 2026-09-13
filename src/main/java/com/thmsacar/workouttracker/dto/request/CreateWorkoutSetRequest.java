package com.thmsacar.workouttracker.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkoutSetRequest {
    private Long exerciseId;
    private Integer setOrder;
    private Double weight;
    private Integer reps;
//    private Integer rpe;
}