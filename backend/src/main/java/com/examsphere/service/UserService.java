package com.examsphere.service;
import com.examsphere.exception.UserNotFoundException;
import com.examsphere.entity.User;
import com.examsphere.entity.Role;
import com.examsphere.repository.RoleRepository;
import com.examsphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.examsphere.dto.UserRequest;
import com.examsphere.dto.UserResponse;
import com.examsphere.dto.LoginRequest;
import com.examsphere.dto.LoginResponse;
import com.examsphere.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
private ModelMapper modelMapper;

@Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private JwtUtil jwtUtil;
    // Save User
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By ID
    public User getUserById(Long id) {

    return userRepository.findById(id)
            .orElseThrow(() ->
                    new UserNotFoundException("User with ID " + id + " not found"));

}

    // Update User
    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());

            return userRepository.save(user);
        }

        return null;
    }

    // Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public UserResponse saveUser(UserRequest request) {

    User user = new User();

    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());

    // Encrypt password
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    Role role = roleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // IMPORTANT: Set the role BEFORE saving
    user.setRole(role);

    User savedUser = userRepository.save(user);

    UserResponse response = new UserResponse();
    response.setId(savedUser.getId());
    response.setFullName(savedUser.getFullName());
    response.setEmail(savedUser.getEmail());
    response.setRole(savedUser.getRole().getRoleName());

    return response;
}
public LoginResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid Email"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid Password");
    }

    LoginResponse response = new LoginResponse();

    response.setId(user.getId());
    response.setFullName(user.getFullName());
    response.setEmail(user.getEmail());
    response.setRole(user.getRole().getRoleName());
    String token = jwtUtil.generateToken(user.getEmail());
response.setToken(token);

    response.setMessage("Login Successful");

    return response;
}
@Override
public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

    return org.springframework.security.core.userdetails.User
            .builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().getRoleName())
            .build();
}

}
