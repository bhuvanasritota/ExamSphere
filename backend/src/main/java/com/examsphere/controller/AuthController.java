package com.examsphere.controller;

import org.springframework.web.bind.annotation.*;
import com.examsphere.entity.User;
import com.examsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

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
@GetMapping("/users")
public List<User> getAllUsers() {
    return userService.getAllUsers();
}

@GetMapping("/users/{id}")
public User getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
}

@PutMapping("/users/{id}")
public User updateUser(@PathVariable Long id,
                       @RequestBody User user) {
    return userService.updateUser(id, user);
}

@DeleteMapping("/users/{id}")
public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "User Deleted Successfully";
}
}