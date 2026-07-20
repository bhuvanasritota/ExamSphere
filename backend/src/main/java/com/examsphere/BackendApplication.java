package com.examsphere;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.examsphere.repository.RoleRepository;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner check(RoleRepository roleRepository) {
        return args -> {
            System.out.println("================================");
            System.out.println(roleRepository.findAll());
            System.out.println("================================");
        };
    }
}