package com.example.cardatabase_v2.PresentationLayer.dto.car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarRequest(

        @NotBlank String brand,
        @NotBlank String model,
        @NotBlank String color,
        @NotBlank String registrationNumber,
        @NotNull @Min(1900) Integer modelYear,
        @NotNull @Min(0) Integer price,
        @NotNull @Min(0) Long ownerId
) {

}
