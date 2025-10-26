package com.example.cardatabase_v2.PresentationLayer.dto.car;

public record OwnerSummary(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
