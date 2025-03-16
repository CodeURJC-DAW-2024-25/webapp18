package es.codeurjc.dto;


public record RoomBasicDTO(
        Long id,
        int maxClients,
        float cost) {
}