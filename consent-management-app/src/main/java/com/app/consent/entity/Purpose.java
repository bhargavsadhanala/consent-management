package com.app.consent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purpose {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purposeId;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dateCreated;

    private LocalDateTime dateModified;
}
