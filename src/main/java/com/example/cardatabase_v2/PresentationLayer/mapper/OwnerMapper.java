package com.example.cardatabase_v2.PresentationLayer.mapper;

import com.example.cardatabase_v2.DataAccessLayer.Owner;
import com.example.cardatabase_v2.PresentationLayer.dto.owner.OwnerRequest;
import com.example.cardatabase_v2.PresentationLayer.dto.owner.OwnerResponse;

public final class OwnerMapper {
    private OwnerMapper() {
    }

    public static Owner toEntity(OwnerRequest req) {
        return Owner.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .phone(req.phone())
                .build();
    }

    public static OwnerResponse toResponse(Owner owner) {
        long count = owner.getCars() == null ? 0 : owner.getCars().size();
        return new OwnerResponse(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getPhone(),
                owner.getCreatedAt(),
                owner.getUpdatedAt(),
                count
        );
    }
}
