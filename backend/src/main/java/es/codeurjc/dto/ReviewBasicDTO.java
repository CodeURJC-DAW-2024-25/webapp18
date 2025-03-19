package es.codeurjc.dto;
import java.time.LocalDate;

public record ReviewBasicDTO (
    Long reviewId,
    int score,
    LocalDate date,
    String comment
) {}
