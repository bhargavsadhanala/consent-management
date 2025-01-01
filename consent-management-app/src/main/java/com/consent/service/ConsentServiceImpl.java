package com.consent.service;

import com.consent.dto.ConsentRequestDTO;
import com.consent.dto.ConsentResponseDTO;
import com.consent.entity.Consent;
import com.consent.entity.ConsentId;
import com.consent.entity.ConsentStatus;
import com.consent.mapper.ConsentAuditLogMapper;
import com.consent.mapper.ConsentMapper;
import com.consent.mapper.ConsentResponseMapper;
import com.consent.repository.ConsentAuditLogRepository;
import com.consent.repository.ConsentRepository;
import com.consent.repository.PurposeRepository;
import com.consent.repository.UserRepository;
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
    public Consent createConsent(ConsentRequestDTO consentRequestDTO) {

        if (!userRepository.existsById(consentRequestDTO.userId())) {
            throw new EntityNotFoundException("User Not Found : " + consentRequestDTO.userId());
        }
        if (!purposeRepository.existsById(consentRequestDTO.purposeId())) {
            throw new EntityNotFoundException("Purpose Not Found: " + consentRequestDTO.purposeId());
        }

        Consent consent = consentMapper.toEntity(consentRequestDTO);
        return consentRepository.save(consent);
    }

    @Override
    public Consent updateConsent(ConsentRequestDTO consentRequest) {
        ConsentId consentId = new ConsentId(consentRequest.userId(), consentRequest.purposeId());
        Consent existingConsent = consentRepository.findById(consentId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Consent doesn't exist with UserId : " + consentRequest.userId() + " and PurposeId : " + consentRequest.purposeId()));
        Consent updatedConsent = consentMapper.updateEntity(existingConsent, ConsentStatus.valueOf(consentRequest.status()), false);
        consentAuditLogRepository.save(consentAuditLogMapper.fromConsent(existingConsent));
        return consentRepository.save(updatedConsent);
    }

    @Override
    public List<ConsentResponseDTO> retrieveConsents(Long userId) {
        List<Consent> consents = consentRepository.findAllByConsentId_UserId(userId);

        return consents
                .stream()
                .map(consentResponseMapper::fromConsent).toList();
    }

    @Override
    public Consent deleteConsent(Long userId, Long purposeId) {
        ConsentId consentId = new ConsentId(userId, purposeId);
        Consent existingConsent = consentRepository.findById(consentId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Consent doesn't exist with UserId : " + userId + " and PurposeId : " + purposeId));

        Consent updatedConsent = consentMapper.updateEntity(existingConsent, ConsentStatus.REVOKED, true);
        //save
        consentAuditLogRepository.save(consentAuditLogMapper.fromConsent(existingConsent));
        return consentRepository.save(updatedConsent);
    }
}