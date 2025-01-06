package com.app.consent.controller;

import com.app.consent.entity.Purpose;
import com.app.consent.service.PurposeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/purposes")
public class PurposeController {

    private final PurposeService purposeService;

    public PurposeController(PurposeService purposeService) {
        this.purposeService = purposeService;
    }

    @PostMapping
    public ResponseEntity<Purpose> addPurpose(@Valid @RequestBody Purpose purpose) {
        return ResponseEntity.ok(purposeService.addPurpose(purpose));
    }

    @GetMapping
    public ResponseEntity<List<Purpose>> getAllPurposes() {
        return ResponseEntity.ok(purposeService.getAllPurposes());
    }

    @GetMapping("/{purposeId}")
    public ResponseEntity<Purpose> getPurpose(@PathVariable(name = "purposeId") Long purposeId) {
        return ResponseEntity.ok(purposeService.fetchPurpose(purposeId));
    }
}
