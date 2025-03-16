package es.codeurjc.dto;

import es.codeurjc.dto.NewReservationDTO;
import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.model.Reservation;

public class ReservationMapper {

    public static Reservation toEntity(NewReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(dto.getCheckIn());
        reservation.setCheckOut(dto.getCheckOut());
        reservation.setNumPeople(dto.getNumPeople());
        return reservation;
    }

    public static ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setCheckIn(reservation.getCheckIn());
        dto.setCheckOut(reservation.getCheckOut());
        dto.setNumPeople(reservation.getNumPeople());
        return dto;
    }
}