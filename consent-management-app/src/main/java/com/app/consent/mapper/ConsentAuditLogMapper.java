package com.app.consent.mapper;

import com.app.consent.entity.Consent;
import com.app.consent.entity.ConsentAuditLog;
import org.springframework.stereotype.Component;

@Component
public class ConsentAuditLogMapper {

    public ConsentAuditLog fromConsent(Consent consent) {
        return new ConsentAuditLog(
                null,
                consent.getConsentId().getUserId(),
                consent.getConsentId().getPurposeId(),
                consent.getStatus(),
                consent.getDateCreated(),
                consent.getDateModified()
        );
    }

}
