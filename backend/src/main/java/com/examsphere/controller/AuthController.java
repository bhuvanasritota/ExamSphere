package com.examsphere.controller;
import com.examsphere.dto.LoginRequest;
import com.examsphere.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;
import com.examsphere.entity.User;
import com.examsphere.service.UserService;
import com.examsphere.entity.Role;
import com.examsphere.service.RoleService;
import jakarta.validation.Valid;
import com.examsphere.dto.UserRequest;
import com.examsphere.dto.UserResponse;
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
    @PostMapping("/register")
public UserResponse saveUser(@Valid @RequestBody UserRequest request) {

    System.out.println("Controller called");
    System.out.println("Full Name = " + request.getFullName());
    System.out.println("Email = " + request.getEmail());
    System.out.println("Password = " + request.getPassword());
    System.out.println("Role ID = " + request.getRoleId());

    return userService.saveUser(request);
}
@PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest request) {

    System.out.println("Login API Called");
    System.out.println("Email = " + request.getEmail());

    return userService.login(request);
}

@Autowired
private RoleService roleService;

@PostMapping("/roles")
public Role saveRole(@RequestBody Role role) {
    return roleService.saveRole(role);
}

@GetMapping("/roles")
public List<Role> getAllRoles() {
    return roleService.getAllRoles();
}
}