package com.thmsacar.workouttracker.repository;

import com.thmsacar.workouttracker.model.Exercise;
import com.thmsacar.workouttracker.model.enums.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    boolean existsByName(String name);

    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);
}