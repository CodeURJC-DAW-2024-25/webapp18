package es.codeurjc.dto;

public record RoomDTO(
        Long id,
        int maxClients,
        float cost,
        ApartmentBasicDTO apartment) {
}