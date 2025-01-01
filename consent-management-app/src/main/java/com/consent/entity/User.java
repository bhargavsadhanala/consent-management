package com.consent.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Nonnull
    @Length(min = 3, max = 50, message = "Length should be min 3")
    @Column(nullable = false)
    private String userName;
    private String gender;

    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status        status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
}
