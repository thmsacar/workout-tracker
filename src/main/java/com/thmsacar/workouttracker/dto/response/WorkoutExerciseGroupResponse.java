package com.thmsacar.workouttracker.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WorkoutExerciseGroupResponse {
    private Long exerciseId;
    private String exerciseName;
    private String muscleGroup;
    private List<WorkoutSetResponse> sets;
}