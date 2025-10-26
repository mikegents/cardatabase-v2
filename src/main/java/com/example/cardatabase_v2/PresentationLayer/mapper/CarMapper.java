package com.example.cardatabase_v2.PresentationLayer.mapper;

import com.example.cardatabase_v2.DataAccessLayer.Car;
import com.example.cardatabase_v2.DataAccessLayer.Owner;
import com.example.cardatabase_v2.PresentationLayer.dto.car.CarRequest;
import com.example.cardatabase_v2.PresentationLayer.dto.car.CarResponse;
import com.example.cardatabase_v2.PresentationLayer.dto.car.OwnerSummary;

public final class CarMapper {
    private CarMapper() {
    }
    public static Car toEntity(CarRequest req, Owner owner) {
        return Car.builder()
                .brand(req.brand())
                .model(req.model())
                .color(req.color())
                .registrationNumber(req.registrationNumber())
                .modelYear(req.modelYear())
                .price(req.price())
                .owner(owner)
                .build();
    }

    public static CarResponse toResponse(Car car) {
        Owner o = car.getOwner();
        OwnerSummary ownerDto = (o == null) ? null : new OwnerSummary(
                o.getId(),
                o.getFirstName(),
                o.getLastName(),
                o.getEmail(),
                o.getPhone()
        );
        return new CarResponse(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getColor(),
                car.getRegistrationNumber(),
                car.getModelYear(),
                car.getPrice(),
                ownerDto
        );
    }
}

