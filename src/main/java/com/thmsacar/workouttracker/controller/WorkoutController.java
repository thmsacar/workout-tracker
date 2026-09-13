package com.thmsacar.workouttracker.controller;

import com.thmsacar.workouttracker.dto.request.CreateWorkoutRequest;
import com.thmsacar.workouttracker.dto.request.UpdateWorkoutRequest;
import com.thmsacar.workouttracker.dto.response.WorkoutResponse;
import com.thmsacar.workouttracker.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    // POST /api/v1/workouts/
    @PostMapping
    public ResponseEntity<WorkoutResponse> createWorkout(@RequestBody CreateWorkoutRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.createWorkout(request));
    }

    // GET /api/v1/workouts/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutResponse>> getUserWorkouts(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(userId));
    }

    // PUT /api/v1/workouts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResponse> updateWorkout(
            @PathVariable Long id,
            @RequestBody UpdateWorkoutRequest request) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, request));
    }

    // DELETE /api/v1/workouts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/v1/workouts/start-from-routine/{routineId}
    @PostMapping("/start-from-routine/{routineId}")
    public ResponseEntity<WorkoutResponse> startFromRoutine(@PathVariable Long routineId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.startWorkoutFromRoutine(routineId));
    }
}