package com.app.consent.service;

import com.app.consent.dto.UserResponseDTO;
import com.app.consent.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDTO saveUser(User user);

    UserResponseDTO fetchUser(Long userId);

    List<UserResponseDTO> fetchAllUsers();

    User updateUser(User user, Long userId);
}
