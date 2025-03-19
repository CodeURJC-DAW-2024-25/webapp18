package es.codeurjc.dto;

import java.util.List;

public record RoomDTO(
        Long id,
        int maxClients,
        float cost,
        List<ReservationBasicDTO> reservations,
        ApartmentBasicDTO apartment) {
}