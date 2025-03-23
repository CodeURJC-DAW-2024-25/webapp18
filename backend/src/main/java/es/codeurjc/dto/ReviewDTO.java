package es.codeurjc.dto;

import java.sql.Date;

public record ReviewDTO (
    Long id,
    int score,
    String comment,
    Date date,
    ApartmentBasicDTO apartment,
    UserBasicDTO user
) {}
