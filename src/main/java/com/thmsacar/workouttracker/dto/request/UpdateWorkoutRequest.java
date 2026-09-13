package com.thmsacar.workouttracker.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateWorkoutRequest {
    private String title;
    private String notes;
    private List<CreateWorkoutSetRequest> sets;
}