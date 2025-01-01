package com.consent.controller;

import com.consent.entity.User;
import com.consent.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.saveUser(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> fetchUser(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(userServiceImpl.fetchUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchUsers() {
        return ResponseEntity.ok(userServiceImpl.fetchAllUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable(name = "userId") Long userId, @Valid @RequestBody User user) {
        userServiceImpl.updateUser(user, userId);
        return ResponseEntity.ok("User updated successfully");

    }
}
