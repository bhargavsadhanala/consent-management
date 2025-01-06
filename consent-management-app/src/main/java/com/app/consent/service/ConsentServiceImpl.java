package com.app.consent.service;

import com.app.consent.entity.ConsentId;
import com.app.consent.repository.ConsentRepository;
import com.app.consent.repository.PurposeRepository;
import com.app.consent.repository.UserRepository;
import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.dto.ConsentResponseDTO;
import com.app.consent.entity.Consent;
import com.app.consent.entity.ConsentStatus;
import com.app.consent.mapper.ConsentAuditLogMapper;
import com.app.consent.mapper.ConsentMapper;
import com.app.consent.mapper.ConsentResponseMapper;
import com.app.consent.repository.ConsentAuditLogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsentServiceImpl implements ConsentService {

    private final UserRepository            userRepository;
    private final PurposeRepository         purposeRepository;
    private final ConsentRepository         consentRepository;
    private final ConsentAuditLogRepository consentAuditLogRepository;
    private final ConsentAuditLogMapper     consentAuditLogMapper;
    private final ConsentMapper             consentMapper;
    private final ConsentResponseMapper     consentResponseMapper;

    public ConsentServiceImpl(
            UserRepository userRepository,
            PurposeRepository purposeRepository,
            ConsentRepository consentRepository,
            ConsentAuditLogRepository consentAuditLogRepository,
            ConsentAuditLogMapper consentAuditLogMapper,
            ConsentMapper consentMapper,
            ConsentResponseMapper consentResponseMapper
    ) {
        this.userRepository = userRepository;
        this.purposeRepository = purposeRepository;
        this.consentRepository = consentRepository;
        this.consentAuditLogRepository = consentAuditLogRepository;
        this.consentAuditLogMapper = consentAuditLogMapper;
        this.consentMapper = consentMapper;
        this.consentResponseMapper = consentResponseMapper;
    }

    @Override
    public ConsentResponseDTO createConsent(ConsentRequestDTO consentRequestDTO) {

        if (!userRepository.existsById(consentRequestDTO.userId())) {
            throw new EntityNotFoundException("User Not Found : " + consentRequestDTO.userId());
        }
        if (!purposeRepository.existsById(consentRequestDTO.purposeId())) {
            throw new EntityNotFoundException("Purpose Not Found: " + consentRequestDTO.purposeId());
        }

        Consent consent = consentMapper.toEntity(consentRequestDTO);
        consentRepository.save(consent);
        return consentResponseMapper.fromConsent(consent);
    }

    @Override
    public ConsentResponseDTO updateConsent(ConsentRequestDTO consentRequest) {
        ConsentId consentId = new ConsentId(consentRequest.userId(), consentRequest.purposeId());
        Consent existingConsent = consentRepository.findById(consentId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Consent doesn't exist with UserId : " + consentRequest.userId() + " and PurposeId : " + consentRequest.purposeId()));
        Consent updatedConsent = consentMapper.updateEntity(existingConsent, ConsentStatus.valueOf(consentRequest.status()), false);
        consentAuditLogRepository.save(consentAuditLogMapper.fromConsent(existingConsent));
        consentRepository.save(updatedConsent);
        return consentResponseMapper.fromConsent(updatedConsent);
    }

    @Override
    public List<ConsentResponseDTO> retrieveConsents(Long userId) {
        List<Consent> consents = consentRepository.findAllByConsentId_UserId(userId);

        return consents
                .stream()
                .map(consentResponseMapper::fromConsent).toList();
    }

    @Override
    public void deleteConsent(Long userId, Long purposeId) {
        ConsentId consentId = new ConsentId(userId, purposeId);
        Consent existingConsent = consentRepository.findById(consentId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Consent doesn't exist with UserId : " + userId + " and PurposeId : " + purposeId));

        Consent updatedConsent = consentMapper.updateEntity(existingConsent, ConsentStatus.REVOKED, true);
        //save
        consentAuditLogRepository.save(consentAuditLogMapper.fromConsent(existingConsent));
        consentRepository.save(updatedConsent);
    }
}