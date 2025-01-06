package com.app.consent.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consent {

    @EmbeddedId
    private ConsentId     consentId;
    @Enumerated(EnumType.STRING)
    private ConsentStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private boolean isDeleted;
}
