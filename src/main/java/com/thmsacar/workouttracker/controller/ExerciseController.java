package com.thmsacar.workouttracker.controller;

import com.thmsacar.workouttracker.model.Exercise;
import com.thmsacar.workouttracker.model.enums.MuscleGroup;
import com.thmsacar.workouttracker.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    //Get all exercises:        GET /api/v1/exercises
    //Filter by muscle group:   GET /api/v1/exercises?muscleGroup=CHEST
    @GetMapping
    public ResponseEntity<List<Exercise>> getExercises(
            @RequestParam(required = false) MuscleGroup muscleGroup) {

        if (muscleGroup != null) {
            return ResponseEntity.ok(exerciseService.getExercisesByMuscleGroup(muscleGroup));
        }
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    //Get single exercise:      GET /api/v1/exercises/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise created = exerciseService.createExercise(exercise);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, exercise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}