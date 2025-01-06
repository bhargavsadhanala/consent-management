package com.app.consent.dto;

import com.app.consent.entity.ConsentStatus;

import java.time.LocalDateTime;

public record ConsentResponseDTO(Long userId, Long purposeId, ConsentStatus consentStatus, LocalDateTime lastUpdated) {

}
