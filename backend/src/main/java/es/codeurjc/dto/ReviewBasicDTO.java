package es.codeurjc.dto;
import java.time.LocalDate;

import java.sql.Date;

public record ReviewBasicDTO (
    Long reviewId,
    int score,
    Date date,
    String comment
) {}
