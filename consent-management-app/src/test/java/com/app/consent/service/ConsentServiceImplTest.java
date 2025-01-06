package com.app.consent.service;

import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.dto.ConsentResponseDTO;
import com.app.consent.entity.Consent;
import com.app.consent.entity.ConsentAuditLog;
import com.app.consent.entity.ConsentId;
import com.app.consent.entity.ConsentStatus;
import com.app.consent.mapper.ConsentAuditLogMapper;
import com.app.consent.mapper.ConsentMapper;
import com.app.consent.mapper.ConsentResponseMapper;
import com.app.consent.repository.ConsentAuditLogRepository;
import com.app.consent.repository.ConsentRepository;
import com.app.consent.repository.PurposeRepository;
import com.app.consent.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ConsentServiceImplTest {

    private final UserRepository userRepository = mock();

    private final PurposeRepository purposeRepository = mock();

    private final ConsentRepository consentRepository = mock();

    private final ConsentAuditLogRepository consentAuditLogRepository = mock();

    private final ConsentAuditLogMapper consentAuditLogMapper = mock();

    private final ConsentMapper consentMapper = mock();

    private final ConsentResponseMapper consentResponseMapper = mock();

    @InjectMocks
    private ConsentServiceImpl consentService =
            new ConsentServiceImpl(userRepository, purposeRepository, consentRepository, consentAuditLogRepository,
                    consentAuditLogMapper, consentMapper, consentResponseMapper);

    private ConsentRequestDTO consentRequestDTO;
    private ConsentId         consentId;

    @BeforeEach
    void setUp() {
        consentId = new ConsentId(1L, 1L);
        consentRequestDTO = createConsentRequestDTO(consentId.getUserId(), consentId.getPurposeId());
    }

    @Test
    void shouldCreateConsent_WhenUserAndPurposeExist() {
        // Given
        Consent consentEntity = createConsentEntity(consentId.getUserId(), consentId.getPurposeId(), ConsentStatus.GRANTED);

        when(userRepository.existsById(consentRequestDTO.userId())).thenReturn(true);
        when(consentRepository.save(any(Consent.class))).thenReturn(consentEntity);
        when(purposeRepository.existsById(consentRequestDTO.purposeId())).thenReturn(true);
        when(consentMapper.toEntity(any(ConsentRequestDTO.class))).thenReturn(consentEntity);
        when(consentResponseMapper.fromConsent(any(Consent.class))).thenReturn(createConsentResponseDTO(consentEntity));

        // When
        ConsentResponseDTO response = consentService.createConsent(consentRequestDTO);

        // Then
        assertNotNull(response);
        verify(userRepository).existsById(consentRequestDTO.userId());
        verify(purposeRepository).existsById(consentRequestDTO.purposeId());
        verify(consentRepository).save(any(Consent.class));
        verifyNoInteractions(consentAuditLogRepository);
    }

    @Test
    void shouldUpdateConsent_WhenConsentExists() {
        // Given
        Consent existingConsent = createConsentEntity(consentId.getUserId(), consentId.getPurposeId(), ConsentStatus.GRANTED);
        Consent updatedConsent = createConsentEntity(consentId.getUserId(), consentId.getPurposeId(), ConsentStatus.REVOKED);

        when(consentRepository.findById(consentId)).thenReturn(Optional.of(existingConsent));
        when(consentMapper.updateEntity(any(Consent.class), any(ConsentStatus.class), anyBoolean())).thenReturn(updatedConsent);
        when(consentAuditLogMapper.fromConsent(existingConsent)).thenReturn(new ConsentAuditLog());
        when(consentRepository.save(any(Consent.class))).thenReturn(updatedConsent);
        when(consentResponseMapper.fromConsent(any(Consent.class))).thenReturn(createConsentResponseDTO(updatedConsent));

        // When
        ConsentResponseDTO response = consentService.updateConsent(consentRequestDTO);

        // Then
        assertNotNull(response);
        verify(consentRepository).findById(consentId);
        verify(consentRepository).save(any(Consent.class));
        verify(consentAuditLogRepository).save(any(ConsentAuditLog.class));
        verify(consentResponseMapper).fromConsent(any(Consent.class));
    }

    @Test
    void shouldReturnConsents_WhenUserExists() {
        // Given
        List<Consent> consents =
                List.of(createConsentEntity(consentId.getUserId(), consentId.getPurposeId(), ConsentStatus.GRANTED));

        when(consentRepository.findAllByConsentId_UserId(consentRequestDTO.userId())).thenReturn(consents);
        when(consentResponseMapper.fromConsent(any(Consent.class))).thenReturn(createConsentResponseDTO(consents.getFirst()));

        // When
        List<ConsentResponseDTO> response = consentService.retrieveConsents(consentRequestDTO.userId());

        // Then
        assertNotNull(response);
        assertEquals(1, response.size());
        verify(consentRepository).findAllByConsentId_UserId(consentRequestDTO.userId());
        verifyNoInteractions(consentAuditLogRepository);
    }

    @Test
    void shouldDeleteConsent_WhenConsentExists() {
        // Given
        Consent existingConsent = createConsentEntity(consentId.getUserId(), consentId.getPurposeId(), ConsentStatus.GRANTED);

        when(consentRepository.findById(consentId)).thenReturn(Optional.of(existingConsent));
        Consent revokedConsent = createConsentEntity(consentId.getUserId(), consentId.getPurposeId(), ConsentStatus.REVOKED);
        when(consentMapper.updateEntity(existingConsent, ConsentStatus.REVOKED, true)).thenReturn(revokedConsent);
        when(consentAuditLogMapper.fromConsent(existingConsent)).thenReturn(new ConsentAuditLog());
        when(consentRepository.save(any(Consent.class))).thenReturn(revokedConsent);

        // When
        consentService.deleteConsent(consentRequestDTO.userId(), consentRequestDTO.purposeId());

        // Then
        verify(consentRepository).findById(consentId);
        verify(consentRepository).save(any(Consent.class));
        verify(consentAuditLogRepository).save(any(ConsentAuditLog.class));
    }

    private ConsentRequestDTO createConsentRequestDTO(Long userId, Long purposeId) {
        return new ConsentRequestDTO(userId, purposeId, "GRANTED");
    }

    private ConsentResponseDTO createConsentResponseDTO(Consent consent) {
        return new ConsentResponseDTO(consent.getConsentId().getUserId(), consent.getConsentId().getPurposeId(), consent.getStatus(),
                consent.getDateModified());
    }

    private Consent createConsentEntity(Long userId, Long purposeId, ConsentStatus status) {
        return new Consent(new ConsentId(userId, purposeId), status, LocalDateTime.now(), LocalDateTime.now(), false);
    }

}