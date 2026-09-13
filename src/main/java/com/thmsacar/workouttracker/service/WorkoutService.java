package com.thmsacar.workouttracker.service;

import com.thmsacar.workouttracker.dto.request.CreateWorkoutRequest;
import com.thmsacar.workouttracker.dto.request.UpdateWorkoutRequest;
import com.thmsacar.workouttracker.dto.response.WorkoutExerciseGroupResponse;
import com.thmsacar.workouttracker.dto.response.WorkoutResponse;
import com.thmsacar.workouttracker.dto.response.WorkoutSetResponse;
import com.thmsacar.workouttracker.model.*;
import com.thmsacar.workouttracker.repository.ExerciseRepository;
import com.thmsacar.workouttracker.repository.RoutineRepository;
import com.thmsacar.workouttracker.repository.UserRepository;
import com.thmsacar.workouttracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final RoutineRepository routineRepository;

    @Transactional
    public WorkoutResponse createWorkout(CreateWorkoutRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Couldn't find user by ID: " + request.getUserId()));

        Workout workout = Workout.builder()
                .title(request.getTitle())
                .notes(request.getNotes())
                .user(user)
                .build();

        if (request.getSets() != null && !request.getSets().isEmpty()) {
            List<WorkoutSet> sets = request.getSets().stream().map(setReq -> {
                Exercise exercise = exerciseRepository.findById(setReq.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Couldn't find exercise by ID: " + setReq.getExerciseId()));

                return WorkoutSet.builder()
                        .workout(workout)
                        .exercise(exercise)
                        .setOrder(setReq.getSetOrder())
                        .weight(setReq.getWeight())
                        .reps(setReq.getReps())
//                        .rpe(setReq.getRpe())
                        .build();
            }).toList();

            workout.setSets(sets);
        }

        Workout savedWorkout = workoutRepository.save(workout);
        return mapToResponse(savedWorkout);
    }

    public List<WorkoutResponse> getWorkoutsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Couldn't find user by ID: " + userId);
        }
        return workoutRepository.findByUserIdOrderByWorkoutDateDesc(userId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public WorkoutResponse updateWorkout(Long id, UpdateWorkoutRequest request) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find workout by ID: " + id));

        // Update titles and notes
        workout.setTitle(request.getTitle());
        workout.setNotes(request.getNotes());

        // Update sets if updated
        if (request.getSets() != null) {
            workout.getSets().clear(); // orphanRemoval = true , deletes from DB

            List<WorkoutSet> updatedSets = request.getSets().stream().map(setReq -> {
                Exercise exercise = exerciseRepository.findById(setReq.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Couldn't find exercise by ID: " + setReq.getExerciseId()));

                return WorkoutSet.builder()
                        .workout(workout)
                        .exercise(exercise)
                        .setOrder(setReq.getSetOrder())
                        .weight(setReq.getWeight())
                        .reps(setReq.getReps())
//                        .rpe(setReq.getRpe())
                        .build();
            }).toList();

            workout.getSets().addAll(updatedSets);
        }

        Workout updatedWorkout = workoutRepository.save(workout);
        return mapToResponse(updatedWorkout);
    }

    public void deleteWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new RuntimeException("Couldn't find workout to delete by ID: " + id);
        }
        workoutRepository.deleteById(id);
    }

    @Transactional
    public WorkoutResponse startWorkoutFromRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RuntimeException("Couldn't find routine by ID: " + routineId));

        Workout workout = Workout.builder()
                .title(routine.getName())
                .notes(routine.getDescription())
                .user(routine.getUser())
                .build();

        // Add targets as empty sets
        List<WorkoutSet> sets = new java.util.ArrayList<>();
        int currentOrder = 1;

        for (RoutineExercise re : routine.getExercises()) {
            int setKacTane = (re.getTargetSets() != null) ? re.getTargetSets() : 3;
            int targetRep = (re.getTargetReps() != null) ? re.getTargetReps() : 10;

            for (int i = 1; i <= setKacTane; i++) {
                sets.add(WorkoutSet.builder()
                        .workout(workout)
                        .exercise(re.getExercise())
                        .setOrder(currentOrder++)
                        .weight(0.0) // To be filled by user
                        .reps(targetRep)
                        .build());
            }
        }

        workout.setSets(sets);
        return mapToResponse(workoutRepository.save(workout));
    }

    private WorkoutResponse mapToResponse(Workout workout) {
        java.util.List<WorkoutExerciseGroupResponse> exerciseGroups = new java.util.ArrayList<>();
        WorkoutExerciseGroupResponse currentGroup = null;

        java.util.List<WorkoutSet> sortedSets = workout.getSets().stream()
                .sorted(java.util.Comparator.comparing(WorkoutSet::getSetOrder))
                .toList();

        for (WorkoutSet s : sortedSets) {
            Long exId = s.getExercise().getId();

            if (currentGroup == null || !currentGroup.getExerciseId().equals(exId)) {
                currentGroup = WorkoutExerciseGroupResponse.builder()
                        .exerciseId(exId)
                        .exerciseName(s.getExercise().getName())
                        .muscleGroup(s.getExercise().getMuscleGroup() != null ? s.getExercise().getMuscleGroup().name() : null)
                        .sets(new java.util.ArrayList<>())
                        .build();
                exerciseGroups.add(currentGroup);
            }

            currentGroup.getSets().add(WorkoutSetResponse.builder()
                    .id(s.getId())
                    .setOrder(s.getSetOrder())
                    .weight(s.getWeight())
                    .reps(s.getReps())
//                  .rpe(s.getRpe())
                    .build());
        }

        return WorkoutResponse.builder()
                .id(workout.getId())
                .userId(workout.getUser().getId())
                .username(workout.getUser().getUsername())
                .title(workout.getTitle())
                .notes(workout.getNotes())
                .workoutDate(workout.getWorkoutDate())
                .exercises(exerciseGroups)
                .build();
    }
}