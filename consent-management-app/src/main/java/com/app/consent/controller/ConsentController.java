package com.app.consent.controller;

import com.app.consent.service.ConsentService;
import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.dto.ConsentResponseDTO;
import com.app.consent.entity.Consent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ConsentResponseDTO> createConsent(@Valid @RequestBody ConsentRequestDTO consentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consentService.createConsent(consentRequest));
    }

    @PutMapping
    public ResponseEntity<ConsentResponseDTO> updateConsent(@Valid @RequestBody ConsentRequestDTO consentRequest) {
        return ResponseEntity.ok(consentService.updateConsent(consentRequest));
    }

    @GetMapping
    public ResponseEntity<List<ConsentResponseDTO>> retrieveConsents(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(consentService.retrieveConsents(userId));
    }

    @DeleteMapping
    public ResponseEntity<Consent> deleteConsent(@RequestParam(name = "userId") Long userId,
            @RequestParam(name = "purposeId") Long purposeId) {
        consentService.deleteConsent(userId, purposeId);
        return ResponseEntity.noContent().build();
    }

}