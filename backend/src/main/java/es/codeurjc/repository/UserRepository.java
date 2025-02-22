package es.codeurjc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.UserE;

@Repository
public interface UserRepository extends JpaRepository<UserE, Long> {
    Optional<UserE> findByNick(String nick);
    Optional<UserE> findByEmail(String email);
    Optional<UserE> findFirstByName(String name);
    Optional<UserE> findByName(String name);
    List<UserE> findByValidatedAndRejected(Boolean validated, Boolean rejected);
    List<UserE> findByRejected(Boolean validated);
    List<UserE> findByPhone(String phone);
    List<UserE> findLocationByName(String name);

    @Query("SELECT DISTINCT u FROM UserE u JOIN u.reservations r WHERE r.apartment = :apartment")
    List<UserE> findByApartmentInReservations(@Param("apartment") Apartment apartment);
}