package es.codeurjc.dto;

import java.time.LocalDate;

public record ReservationDTO(
    Long id,
    LocalDate checkIn,
    LocalDate checkOut,
    Integer numPeople,
    ApartmentBasicDTO apartment,
    RoomBasicDTO room,
    UserBasicDTO user
) {}