package com.consent.service;

import com.consent.entity.Purpose;
import com.consent.repository.PurposeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurposeServiceImpl implements PurposeService {

    private final PurposeRepository purposeRepository;

    public PurposeServiceImpl(PurposeRepository purposeRepository) {
        this.purposeRepository = purposeRepository;
    }

    @Override
    public Purpose addPurpose(Purpose purpose) {
        return purposeRepository.save(purpose);
    }

    @Override
    public Purpose fetchPurpose(Long purposeId) {
        return purposeRepository.findById(purposeId).orElse(null);
    }

    @Override
    public List<Purpose> getAllPurposes() {
        return purposeRepository.findAll();
    }

    @Override
    public Purpose updatePurpose() {
        return null;
    }

    @Override
    public Purpose deletePurpose() {
        return null;
    }
}
