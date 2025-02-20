package es.codeurjc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.backend.model.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findByName(String name);

    List<Apartment> findByLocation(String location);

    List<Apartment> findTop6ByManager_Validated(Boolean validated);

    List<Apartment> findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(Boolean validated,
            String searchValue);
}

