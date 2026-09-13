package com.thmsacar.workouttracker.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class WorkoutResponse {
    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String notes;
    private LocalDateTime workoutDate;
    private List<WorkoutExerciseGroupResponse> exercises; //Grouped list
}