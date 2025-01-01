package com.consent.service;

import com.consent.entity.User;
import com.consent.exception.ResourceNotFoundException;
import com.consent.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User fetchUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With Id :" + userId));
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user, Long userId) {
        return userRepository.save(user);
    }
}
