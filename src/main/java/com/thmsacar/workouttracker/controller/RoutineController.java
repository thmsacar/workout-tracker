package com.thmsacar.workouttracker.controller;

import com.thmsacar.workouttracker.dto.request.CreateRoutineRequest;
import com.thmsacar.workouttracker.dto.request.UpdateRoutineRequest;
import com.thmsacar.workouttracker.dto.response.RoutineResponse;
import com.thmsacar.workouttracker.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    // POST /api/v1/routines
    @PostMapping
    public ResponseEntity<RoutineResponse> createRoutine(@RequestBody CreateRoutineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routineService.createRoutine(request));
    }

    // GET /api/v1/routines/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RoutineResponse>> getUserRoutines(@PathVariable Long userId) {
        return ResponseEntity.ok(routineService.getUserRoutines(userId));
    }

    // DELETE /api/v1/routines/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/v1/routines/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RoutineResponse> updateRoutine(
            @PathVariable Long id,
            @RequestBody UpdateRoutineRequest request) {
        return ResponseEntity.ok(routineService.updateRoutine(id, request));
    }
}