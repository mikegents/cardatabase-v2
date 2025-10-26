package com.example.cardatabase_v2.PresentationLayer.dto.car;

public record CarResponse(
        Long id,
        String brand,
        String model,
        String color,
        String registrationNumber,
        int modelYear,
        int price,
        OwnerSummary owner
) {
}

