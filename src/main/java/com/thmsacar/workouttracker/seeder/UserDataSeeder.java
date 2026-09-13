package com.thmsacar.workouttracker.seeder;

import com.thmsacar.workouttracker.model.User;
import com.thmsacar.workouttracker.model.enums.Role;
import com.thmsacar.workouttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        List<User> defaultUsers = List.of(
                User.builder()
                        .username("admin")
                        .email("admin@workout.com")
                        .password("admin123") //TODO encryption with BCrypt
                        .role(Role.ADMIN)
                        .build(),

                User.builder()
                        .username("thomas")
                        .email("thomas@workout.com")
                        .password("pass123")
                        .role(Role.USER)
                        .build()
        );

        userRepository.saveAll(defaultUsers);
        System.out.println(">> USER SEEDER: Users added successfully.");
    }
}