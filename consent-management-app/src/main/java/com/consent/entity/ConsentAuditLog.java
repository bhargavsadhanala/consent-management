package com.consent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsentAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private Long      userId;

    private Long purposeId;

    @Enumerated(EnumType.STRING)
    private ConsentStatus status;

    private LocalDateTime dateCreated;

    private LocalDateTime dateModified;
}
