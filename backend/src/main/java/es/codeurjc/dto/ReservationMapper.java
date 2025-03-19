package es.codeurjc.dto;

import es.codeurjc.dto.NewReservationDTO;
import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.dto.ReservationBasicDTO;
import es.codeurjc.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "apartmentId", target = "apartment.id")
    @Mapping(source = "roomId", target = "room.id")
    @Mapping(source = "userId", target = "user.id")
    Reservation toEntity(NewReservationDTO dto);

    @Mapping(source = "apartment.id", target = "apartmentId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    ReservationDTO toDTO(Reservation reservation);

    ReservationBasicDTO toBasicDTO(Reservation reservation);
}