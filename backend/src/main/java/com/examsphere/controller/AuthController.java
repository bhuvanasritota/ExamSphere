package com.examsphere.controller;

import org.springframework.web.bind.annotation.*;
import com.examsphere.entity.User;
import com.examsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AuthController {
    @Autowired
private UserService userService;

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
    @PostMapping("/users")
public User saveUser(@RequestBody User user) {
    return userService.saveUser(user);
}
}