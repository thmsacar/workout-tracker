package com.thmsacar.workouttracker.service;

import com.thmsacar.workouttracker.model.Exercise;
import com.thmsacar.workouttracker.model.enums.MuscleGroup;
import com.thmsacar.workouttracker.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public List<Exercise> getExercisesByMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find exercise by ID: " + id));
    }

    public Exercise createExercise(Exercise exercise) {
        if (exerciseRepository.existsByName(exercise.getName())) {
            throw new RuntimeException("An exercise by this name already exists: " + exercise.getName());
        }
        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(Long id, Exercise updatedData) {
        Exercise existing = getExerciseById(id);

        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setCategory(updatedData.getCategory());
        existing.setMuscleGroup(updatedData.getMuscleGroup());

        return exerciseRepository.save(existing);
    }

    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new RuntimeException("Couldn't find the exercise to delete by ID: " + id);
        }
        exerciseRepository.deleteById(id);
    }
}