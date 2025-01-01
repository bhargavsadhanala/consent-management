package com.consent.mapper;

import com.consent.dto.ConsentResponseDTO;
import com.consent.entity.Consent;
import org.springframework.stereotype.Component;

@Component
public class ConsentResponseMapper {

    public ConsentResponseDTO fromConsent(Consent consent) {
        return new ConsentResponseDTO(
                consent.getConsentId().getUserId(),
                consent.getConsentId().getPurposeId(),
                consent.getStatus(),
                consent.getDateModified()
        );
    }
}
