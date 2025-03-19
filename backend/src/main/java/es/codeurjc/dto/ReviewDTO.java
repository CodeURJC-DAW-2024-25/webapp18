package es.codeurjc.dto;
import java.sql.Date;

public record ReviewDTO (
    Long reviewId,
    UserBasicDTO user,
    ApartmentBasicDTO apartment,
    int score,
    Date date,
    String comment

) {}

