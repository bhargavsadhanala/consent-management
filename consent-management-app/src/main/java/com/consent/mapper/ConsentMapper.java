package com.consent.mapper;

import com.consent.dto.ConsentRequestDTO;
import com.consent.entity.Consent;
import com.consent.entity.ConsentId;
import com.consent.entity.ConsentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConsentMapper {

    public Consent toEntity(ConsentRequestDTO consentRequest) {
        Consent consent = new Consent();
        consent.setConsentId(new ConsentId(consentRequest.userId(), consentRequest.purposeId()));
        consent.setStatus(ConsentStatus.fromValue(consentRequest.status()));
        consent.setDateCreated(LocalDateTime.now());
        consent.setDateModified(LocalDateTime.now());
        return consent;
    }

    public Consent updateEntity(Consent existingConsent, ConsentStatus consentStatus, boolean isDelete) {
        existingConsent.setStatus(consentStatus);
        existingConsent.setDateModified(LocalDateTime.now());
        existingConsent.setDeleted(isDelete);
        return existingConsent;
    }
}