package com.consent.service;

import com.consent.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User fetchUser(Long userId);

    List<User> fetchAllUsers();

    User updateUser(User user, Long userId);
}
