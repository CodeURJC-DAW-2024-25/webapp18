package es.codeurjc.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.UserE;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findByName(String name);

    List<Apartment> findByLocation(String location);

    List<Apartment> findTop6ByManager_Validated(Boolean validated);

    List<Apartment> findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(Boolean validated,
            String searchValue);

    Page<Apartment> findByManager(UserE user, Pageable pageable);
}

