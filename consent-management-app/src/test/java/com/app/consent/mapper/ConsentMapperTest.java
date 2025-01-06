package com.app.consent.mapper;

import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.entity.Consent;
import com.app.consent.entity.ConsentId;
import com.app.consent.entity.ConsentStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConsentMapperTest {

    ConsentMapper consentMapper = new ConsentMapper();

    @Test
    void testToEntity() {
        // Given
        ConsentRequestDTO requestDTO = new ConsentRequestDTO(1L, 2L, "GRANTED");

        // When
        Consent consent = consentMapper.toEntity(requestDTO);

        // Then
        assertNotNull(consent);
        assertEquals(new ConsentId(1L, 2L), consent.getConsentId());
        assertEquals(ConsentStatus.GRANTED, consent.getStatus());
        assertNotNull(consent.getDateCreated());
        assertNotNull(consent.getDateModified());
        assertEquals(consent.getDateCreated(), consent.getDateModified());
    }

    @Test
    void testUpdateEntity() {
        // Given
        Consent existingConsent = new Consent();
        existingConsent.setConsentId(new ConsentId(1L, 2L));
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        existingConsent.setDateCreated(LocalDateTime.now().minusHours(1).truncatedTo(ChronoUnit.SECONDS));

        // When
        Consent updatedConsent = consentMapper.updateEntity(existingConsent, ConsentStatus.REVOKED, false);

        // Then
        assertNotNull(updatedConsent);
        assertEquals(ConsentStatus.REVOKED, updatedConsent.getStatus());
        assertEquals(now, updatedConsent.getDateModified());
        assertTrue(updatedConsent.getDateModified().isAfter(existingConsent.getDateCreated()));
        assertFalse(updatedConsent.isDeleted());
    }
}
