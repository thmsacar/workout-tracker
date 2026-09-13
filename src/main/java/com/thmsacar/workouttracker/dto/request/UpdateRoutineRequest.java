package com.thmsacar.workouttracker.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateRoutineRequest {
    private String name;
    private String description;
    private List<RoutineExerciseRequest> exercises;
}