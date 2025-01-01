package com.consent.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
    private ConsentStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private boolean isDeleted;
}
