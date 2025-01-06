package com.app.consent.repository;

import com.app.consent.entity.ConsentId;
import com.app.consent.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsentRepository extends JpaRepository<Consent, ConsentId> {

    List<Consent> findAllByConsentId_UserId(Long userId);

}
