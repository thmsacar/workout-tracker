package com.thmsacar.workouttracker.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoutineResponse {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private List<RoutineExerciseDto> exercises;

    @Getter
    @Builder
    public static class RoutineExerciseDto {
        private Long id;
        private Long exerciseId;
        private String exerciseName;
        private Integer targetSets;
        private Integer targetReps;
        private Integer exerciseOrder;
    }
}