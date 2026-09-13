package com.thmsacar.workouttracker.service;

import com.thmsacar.workouttracker.dto.request.CreateRoutineRequest;
import com.thmsacar.workouttracker.dto.request.UpdateRoutineRequest;
import com.thmsacar.workouttracker.dto.response.RoutineResponse;
import com.thmsacar.workouttracker.model.Exercise;
import com.thmsacar.workouttracker.model.Routine;
import com.thmsacar.workouttracker.model.RoutineExercise;
import com.thmsacar.workouttracker.model.User;
import com.thmsacar.workouttracker.repository.ExerciseRepository;
import com.thmsacar.workouttracker.repository.RoutineRepository;
import com.thmsacar.workouttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public RoutineResponse createRoutine(CreateRoutineRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Couldn't find the user by ID: " + request.getUserId()));

        Routine routine = Routine.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();

        if (request.getExercises() != null) {
            List<RoutineExercise> routineExercises = request.getExercises().stream().map(req -> {
                Exercise exercise = exerciseRepository.findById(req.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Couldn't find the exercise by ID: " + req.getExerciseId()));

                return RoutineExercise.builder()
                        .routine(routine)
                        .exercise(exercise)
                        .targetSets(req.getTargetSets())
                        .targetReps(req.getTargetReps())
                        .exerciseOrder(req.getExerciseOrder())
                        .build();
            }).toList();

            routine.setExercises(routineExercises);
        }

        return mapToResponse(routineRepository.save(routine));
    }

    public List<RoutineResponse> getUserRoutines(Long userId) {
        return routineRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteRoutine(Long id) {
        if (!routineRepository.existsById(id)) {
            throw new RuntimeException("Couldn't find the routine by ID: " + id);
        }
        routineRepository.deleteById(id);
    }

    @Transactional
    public RoutineResponse updateRoutine(Long id, UpdateRoutineRequest request) {
        Routine routine = routineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find routine by ID: " + id));

        routine.setName(request.getName());
        routine.setDescription(request.getDescription());

        if (request.getExercises() != null) {
            routine.getExercises().clear(); // orphanRemoval = true deletes from DB

            List<RoutineExercise> updatedExercises = request.getExercises().stream().map(req -> {
                Exercise exercise = exerciseRepository.findById(req.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Coudln't find exercise by ID: " + req.getExerciseId()));

                return RoutineExercise.builder()
                        .routine(routine)
                        .exercise(exercise)
                        .targetSets(req.getTargetSets())
                        .targetReps(req.getTargetReps())
                        .exerciseOrder(req.getExerciseOrder())
                        .build();
            }).toList();

            routine.getExercises().addAll(updatedExercises);
        }

        return mapToResponse(routineRepository.save(routine));
    }

    private RoutineResponse mapToResponse(Routine routine) {
        List<RoutineResponse.RoutineExerciseDto> exerciseDtos = routine.getExercises().stream()
                .sorted(Comparator.comparing(RoutineExercise::getExerciseOrder))
                .map(re -> RoutineResponse.RoutineExerciseDto.builder()
                        .id(re.getId())
                        .exerciseId(re.getExercise().getId())
                        .exerciseName(re.getExercise().getName())
                        .targetSets(re.getTargetSets())
                        .targetReps(re.getTargetReps())
                        .exerciseOrder(re.getExerciseOrder())
                        .build()
        ).toList();

        return RoutineResponse.builder()
                .id(routine.getId())
                .userId(routine.getUser().getId())
                .name(routine.getName())
                .description(routine.getDescription())
                .exercises(exerciseDtos)
                .build();
    }
}