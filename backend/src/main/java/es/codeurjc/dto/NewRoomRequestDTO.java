package es.codeurjc.dto;


public record NewRoomRequestDTO(
        int maxClients,
        float cost,
        Long apartmentId) {
}