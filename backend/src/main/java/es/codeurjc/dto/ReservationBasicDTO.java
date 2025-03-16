package es.codeurjc.dto;

import java.time.LocalDate;

public class ReservationBasicDTO {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer numPeople;
    private Long apartmentId;
    private Long roomId;
    private Long userId;


}