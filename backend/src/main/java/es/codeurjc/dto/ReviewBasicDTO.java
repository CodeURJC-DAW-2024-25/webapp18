package es.codeurjc.dto;

public record ReviewBasicDTO (
    Long reviewId,
    int score,
    LocalDate date,
    String comment
) {}
