package es.codeurjc.dto;

import java.time.LocalDate;

public record ReservationBasicDTO(
    Long id,
    LocalDate checkIn,
    LocalDate checkOut,
    Integer numPeople
) {}