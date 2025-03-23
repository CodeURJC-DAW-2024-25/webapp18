package es.codeurjc.dto;

import java.sql.Date;

public record ReviewBasicDTO (
    Long id,
    int score,
    String comment,
    Date date
) {}