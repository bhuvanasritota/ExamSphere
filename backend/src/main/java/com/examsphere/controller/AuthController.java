package com.examsphere.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to ExamSphere!";
    }

    @GetMapping("/student")
    public String student() {
        return "Student API Working";
    }

    @PostMapping("/student")
    public String addStudent() {
        return "Student Added Successfully";
    }
}