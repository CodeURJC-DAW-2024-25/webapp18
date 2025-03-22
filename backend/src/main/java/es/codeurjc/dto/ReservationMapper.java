package es.codeurjc.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import es.codeurjc.model.Reservation;

@Mapper(componentModel = "spring")
@Component
public interface ReservationMapper {
    @Mapping(source = "apartmentId", target = "apartment.id")
    @Mapping(source = "roomId", target = "room.id")
    @Mapping(source = "userId", target = "user.id")
    Reservation toDomain(ReservationDTO dto);

    @Mapping(source = "apartment.id", target = "apartmentId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    ReservationDTO toDTO(Reservation reservation);

    ReservationBasicDTO toBasicDTO(Reservation reservation);
}