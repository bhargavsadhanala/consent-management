package com.consent.dto;

import com.consent.entity.ConsentStatus;

import java.time.LocalDateTime;

public record ConsentResponseDTO(Long userId, Long purposeId, ConsentStatus consentStatus, LocalDateTime lastUpdated) {

}
