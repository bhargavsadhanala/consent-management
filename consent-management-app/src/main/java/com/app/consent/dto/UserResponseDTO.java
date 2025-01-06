package com.app.consent.dto;

import com.app.consent.entity.Status;

import java.time.LocalDateTime;

public record UserResponseDTO(Long userId, String userName, String gender, Status status, LocalDateTime lastUpdated) {

}
