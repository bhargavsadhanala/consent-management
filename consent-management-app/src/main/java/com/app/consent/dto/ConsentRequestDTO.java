package com.app.consent.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ConsentRequestDTO(
        @NotNull Long userId,
        @NotNull Long purposeId,
        @NotNull @Pattern(regexp = "GRANTED|REVOKED", message = "Status must be GRANTED or REVOKED") String status
) {

}
