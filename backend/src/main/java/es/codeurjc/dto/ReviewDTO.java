package es.codeurjc.dto;
import java.time.LocalDate;

public record ReviewDTO (
    Long reviewId,
    UserBasicDTO user,
    ApartmentBasicDTO apartment,
    int score,
    LocalDate date,
    String comment

) {}

