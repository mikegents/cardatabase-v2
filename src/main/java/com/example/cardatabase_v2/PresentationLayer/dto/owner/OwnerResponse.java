package com.example.cardatabase_v2.PresentationLayer.dto.owner;

import java.time.Instant;
public record OwnerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Instant createdAt,
        Instant updatedAt,
        long carCount
) {
}