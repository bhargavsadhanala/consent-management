package com.app.consent.service;

import com.app.consent.dto.UserResponseDTO;
import com.app.consent.entity.User;
import com.app.consent.exception.ResourceNotFoundException;
import com.app.consent.mapper.UserResponseMapper;
import com.app.consent.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository        userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserResponseMapper    userResponseMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            UserResponseMapper userResponseMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResponseMapper = userResponseMapper;
    }

    public UserResponseDTO saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return userResponseMapper.fromUser(user);
    }

    public UserResponseDTO fetchUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With Id :" + userId));
        return userResponseMapper.fromUser(user);
    }

    public List<UserResponseDTO> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userResponseMapper::fromUser).toList();
    }

    public User updateUser(User user, Long userId) {
        return userRepository.save(user);
    }
}
