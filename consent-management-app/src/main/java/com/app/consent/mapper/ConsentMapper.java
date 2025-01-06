package com.app.consent.mapper;

import com.app.consent.entity.ConsentId;
import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.entity.Consent;
import com.app.consent.entity.ConsentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ConsentMapper {

    public Consent toEntity(ConsentRequestDTO consentRequest) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Consent consent = new Consent();
        consent.setConsentId(new ConsentId(consentRequest.userId(), consentRequest.purposeId()));
        consent.setStatus(ConsentStatus.fromValue(consentRequest.status()));
        consent.setDateCreated(now);
        consent.setDateModified(now);
        return consent;
    }

    public Consent updateEntity(Consent existingConsent, ConsentStatus consentStatus, boolean isDelete) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        existingConsent.setStatus(consentStatus);
        existingConsent.setDateModified(now);
        existingConsent.setDeleted(isDelete);
        return existingConsent;
    }
}