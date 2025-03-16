package es.codeurjc.dto;

import es.codeurjc.model.Reservation;

public class ReservationMapper {

    public static Reservation toEntity(NewReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setCheckIn(dto.getCheckIn());
        reservation.setCheckOut(dto.getCheckOut());
        reservation.setNumPeople(dto.getNumPeople());
        reservation.setApartmentId(dto.getApartmentId());
        reservation.setRoomId(dto.getRoomId());
        reservation.setUserId(dto.getUserId());
        return reservation;
    }

    public static ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setCheckIn(reservation.getCheckIn());
        dto.setCheckOut(reservation.getCheckOut());
        dto.setNumPeople(reservation.getNumPeople());
        dto.setApartmentId(reservation.getApartmentId());
        dto.setRoomId(reservation.getRoomId());
        dto.setUserId(reservation.getUserId());
        return dto;
    }
}