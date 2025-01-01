package com.consent.controller;

import com.consent.dto.ConsentRequestDTO;
import com.consent.dto.ConsentResponseDTO;
import com.consent.entity.Consent;
import com.consent.service.ConsentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consents")
public class ConsentController {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping
    public ResponseEntity<Consent> createConsent(@Valid @RequestBody ConsentRequestDTO consentRequest) {
        return ResponseEntity.ok(consentService.createConsent(consentRequest));
    }

    @PutMapping
    public ResponseEntity<Consent> updateConsent(@Valid @RequestBody ConsentRequestDTO consentRequest) {
        return ResponseEntity.ok(consentService.updateConsent(consentRequest));
    }

    @GetMapping
    public ResponseEntity<List<ConsentResponseDTO>> retrieveConsents(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(consentService.retrieveConsents(userId));
    }

    @DeleteMapping
    public ResponseEntity<Consent> deleteConsent(@RequestParam(name = "userId") Long userId,
            @RequestParam(name = "purposeId") Long purposeId) {
        return ResponseEntity.ok(consentService.deleteConsent(userId, purposeId));
    }

}