package com.consent.repository;

import com.consent.entity.Consent;
import com.consent.entity.ConsentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsentRepository extends JpaRepository<Consent, ConsentId> {

    List<Consent> findAllByConsentId_UserId(Long userId);

}
