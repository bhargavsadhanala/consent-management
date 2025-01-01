package com.consent.service;

import com.consent.dto.ConsentRequestDTO;
import com.consent.dto.ConsentResponseDTO;
import com.consent.entity.Consent;

import java.util.List;

public interface ConsentService {

    Consent createConsent(ConsentRequestDTO consentRequestDTO);

    Consent updateConsent(ConsentRequestDTO consentRequestDTO);

    List<ConsentResponseDTO> retrieveConsents(Long userId);

    Consent deleteConsent(Long userId, Long purposeId);
}
