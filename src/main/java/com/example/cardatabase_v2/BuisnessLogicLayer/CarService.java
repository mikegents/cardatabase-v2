package com.example.cardatabase_v2.BuisnessLogicLayer;
import com.example.cardatabase_v2.DataAccessLayer.Car;
import com.example.cardatabase_v2.DataAccessLayer.CarRepository;
import com.example.cardatabase_v2.DataAccessLayer.Owner;
import com.example.cardatabase_v2.PresentationLayer.dto.car.CarRequest;
import com.example.cardatabase_v2.PresentationLayer.dto.car.CarResponse;
import com.example.cardatabase_v2.utilities.CarNotFoundException;
import com.example.cardatabase_v2.utilities.DuplicateResourceException;
import com.example.cardatabase_v2.utilities.InvalidCarDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.cardatabase_v2.PresentationLayer.mapper.CarMapper;

import java.time.Year;
import java.util.List;
@Service
public class CarService {
    private final CarRepository carRepository;
    private final OwnerService ownerService;

    public CarService(CarRepository cars, OwnerService owners) {
        this.carRepository = cars;
        this.ownerService = owners;
    }

    @Transactional(readOnly = true)
    public List<CarResponse> getAll() {
        return carRepository.findAll()
                .stream().map(CarMapper::toResponse).toList();
    }
    @Transactional(readOnly = true)
    public CarResponse getById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        return CarMapper.toResponse(car);
    }
    @Transactional
    public CarResponse create(CarRequest req) {
        int currentYear = Year.now().getValue();
        if (req.modelYear() > currentYear) {
            throw new InvalidCarDataException(
                    "Model year (" + req.modelYear() + ") cannot be greater than current year (" + currentYear + ")."
            );
        }
        if (carRepository.existsByRegistrationNumber(req.registrationNumber())) {
            throw new DuplicateResourceException("Registration already exists: " + req.registrationNumber());
        }
        Owner owner = ownerService.getEntityById(req.ownerId());

        Car entity = CarMapper.toEntity(req, owner);
        Car saved = carRepository.save(entity);
        return CarMapper.toResponse(saved);
    }
    @Transactional
    public CarResponse update(Long id, CarRequest req) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        int currentYear = Year.now().getValue();
        if (req.modelYear() > currentYear) {
            throw new InvalidCarDataException(
                    "Model year (" + req.modelYear() + ") cannot be greater than current year (" + currentYear + ")."
            );
        }
        boolean registrationNumberChanging = !car.getRegistrationNumber().equals(req.registrationNumber());
        if (registrationNumberChanging && carRepository.existsByRegistrationNumber(req.registrationNumber())) {
            throw new DuplicateResourceException("Registration already exists: " + req.registrationNumber());
        }

        Owner owner = ownerService.getEntityById(req.ownerId());
        car.setBrand(req.brand());
        car.setModel(req.model());
        car.setColor(req.color());
        car.setRegistrationNumber(req.registrationNumber());
        car.setModelYear(req.modelYear());
        car.setPrice(req.price());
        car.setOwner(owner);

        Car saved = carRepository.save(car);
        return CarMapper.toResponse(saved);
    }
    @Transactional
    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        carRepository.delete(car);
    }
    @Transactional(readOnly = true)
    public List<CarResponse> getCarsByOwner(Long ownerId) {
        // Validate owner exists (throws 404 if not)
        ownerService.getEntityById(ownerId);
        return carRepository.findByOwnerId(ownerId)
                .stream()
                .map(CarMapper::toResponse)
                .toList();
    }
    // One call that covers *all* common filters ----
    @Transactional(readOnly = true)
    public List<CarResponse> search(
            String brand,
            Long ownerId,
            String color,
            Integer minPrice,
            Integer maxPrice,
            Integer minYear,
            Integer maxYear,
            String registrationContains
    ) {
        String brandNorm = normalize(brand);
        String colorNorm = normalize(color);
        String regNorm = normalize(registrationContains);
        return carRepository.searchAll(
                brandNorm, ownerId, colorNorm, minPrice, maxPrice, minYear, maxYear, regNorm
        ).stream().map(CarMapper::toResponse).toList();
    }
    // Helper to normalize strings: trim and convert blank to null
    private static String normalize(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}



