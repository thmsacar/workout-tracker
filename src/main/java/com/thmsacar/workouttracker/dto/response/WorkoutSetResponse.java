package com.thmsacar.workouttracker.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkoutSetResponse {
    private Long id;
    private Integer setOrder;
    private Double weight;
    private Integer reps;
//    private Integer rpe;
}