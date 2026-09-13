package com.thmsacar.workouttracker.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutineExerciseRequest {
    private Long exerciseId;
    private Integer targetSets;
    private Integer targetReps;
    private Integer exerciseOrder;
}