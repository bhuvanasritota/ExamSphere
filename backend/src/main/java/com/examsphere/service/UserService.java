package com.examsphere.service;
import com.examsphere.exception.UserNotFoundException;
import com.examsphere.entity.User;
import com.examsphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.examsphere.dto.UserRequest;
import com.examsphere.dto.UserResponse;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
private ModelMapper modelMapper;

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

    User user = modelMapper.map(request, User.class);

    User savedUser = userRepository.save(user);

    return modelMapper.map(savedUser, UserResponse.class);

}
}