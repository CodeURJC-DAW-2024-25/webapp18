package es.codeurjc.dto;


import java.sql.Date;

public record ReviewBasicDTO (
    Long id,
    int score,
    Date date,
    String comment
) {}
