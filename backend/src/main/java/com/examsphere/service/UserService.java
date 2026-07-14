package com.examsphere.service;
import com.examsphere.exception.UserNotFoundException;
import com.examsphere.entity.User;
import com.examsphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examsphere.dto.UserRequest;
import com.examsphere.dto.UserResponse;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
    user.setPassword(request.getPassword());
    user.setRole(request.getRole());

    User savedUser = userRepository.save(user);

    UserResponse response = new UserResponse();

    response.setId(savedUser.getId());
    response.setFullName(savedUser.getFullName());
    response.setEmail(savedUser.getEmail());
    response.setRole(savedUser.getRole());

    return response;
}

}