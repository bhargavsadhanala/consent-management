package com.app.consent.service;

import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.dto.ConsentResponseDTO;

import java.util.List;

public interface ConsentService {

    ConsentResponseDTO createConsent(ConsentRequestDTO consentRequestDTO);

    ConsentResponseDTO updateConsent(ConsentRequestDTO consentRequestDTO);

    List<ConsentResponseDTO> retrieveConsents(Long userId);

    void deleteConsent(Long userId, Long purposeId);
}
