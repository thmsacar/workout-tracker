package com.thmsacar.workouttracker.seeder;

import com.thmsacar.workouttracker.model.Exercise;
import com.thmsacar.workouttracker.model.enums.Category;
import com.thmsacar.workouttracker.model.enums.MuscleGroup;
import com.thmsacar.workouttracker.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseDataSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) {
        if (exerciseRepository.count() > 0) {
            return;
        }

        List<Exercise> defaultExercises = List.of(
                Exercise.builder()
                        .name("Barbell Bench Press")
                        .description("Flat bench press targeting middle chest and triceps.")
                        .category(Category.STRENGTH)
                        .muscleGroup(MuscleGroup.CHEST)
                        .build(),

                Exercise.builder()
                        .name("Barbell Back Squat")
                        .description("Full depth squat focusing on quadriceps and glutes.")
                        .category(Category.STRENGTH)
                        .muscleGroup(MuscleGroup.LEGS)
                        .build(),

                Exercise.builder()
                        .name("Conventional Deadlift")
                        .description("Compound movement targeting posterior chain.")
                        .category(Category.STRENGTH)
                        .muscleGroup(MuscleGroup.BACK)
                        .build(),

                Exercise.builder()
                        .name("Overhead Press")
                        .description("Standing barbell press targeting anterior deltoids.")
                        .category(Category.STRENGTH)
                        .muscleGroup(MuscleGroup.SHOULDERS)
                        .build(),

                Exercise.builder()
                        .name("Pull-Up")
                        .description("Bodyweight pull-up focusing on latissimus dorsi.")
                        .category(Category.CALISTHENICS)
                        .muscleGroup(MuscleGroup.BACK)
                        .build(),

                Exercise.builder()
                        .name("Treadmill Running")
                        .description("Steady-state cardio session.")
                        .category(Category.CARDIO)
                        .muscleGroup(MuscleGroup.FULL_BODY)
                        .build()
        );

        exerciseRepository.saveAll(defaultExercises);
        System.out.println(">> EXERCISE SEEDER: Exercises added successfully.");
    }
}