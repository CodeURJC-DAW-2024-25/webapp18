package es.codeurjc.dto;

import java.time.LocalDate;

public record ReservationDTO(
    Long idReservation,
    LocalDate checkIn,
    LocalDate checkOut,
    Integer numPeople,
    Long apartmentId,
    Long roomId,
    Long userId
) {}