package com.example.cardatabase_v2.DataAccessLayer;
import com.example.cardatabase_v2.DataAccessLayer.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface CarRepository extends JpaRepository<Car, Long> {
    // ---- Derived queries examples----
    List<Car> findByOwnerId(Long ownerId);

    boolean existsByRegistrationNumber(String registrationNumber);
    // ---- One flexible JPQL query that covers common filters (nullable params are "ignored") ----


    @Query("""
 SELECT c
 FROM Car c
 WHERE (:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
 AND (:ownerId IS NULL OR c.owner.id = :ownerId)
 AND (:color IS NULL OR LOWER(c.color) = LOWER(:color))
 AND (:minPrice IS NULL OR c.price >= :minPrice)
 AND (:maxPrice IS NULL OR c.price <= :maxPrice)
 AND (:minYear IS NULL OR c.modelYear >= :minYear)
 AND (:maxYear IS NULL OR c.modelYear <= :maxYear)
 AND (:regPart IS NULL OR LOWER(c.registrationNumber) LIKE LOWER(CONCAT('%', :regPart, '%')))
 """)

    List<Car> searchAll(
            @Param("brand") String brand,
            @Param("ownerId") Long ownerId,
            @Param("color") String color,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("regPart") String regPart
    );
}





