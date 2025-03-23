package es.codeurjc.dto;


import es.codeurjc.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {ApartmentMapper.class, RoomMapper.class, UserMapper.class})
@Component
public interface ReservationMapper {

    @Mapping(source = "apartment", target = "apartment")
    @Mapping(source = "room", target = "room")
    @Mapping(source = "user", target = "user")
    ReservationDTO toDTO(Reservation reservation);

    /* The warning is probably caused for having
    indirect getters in the Reservation class
    (getApartmentID, getRoomId, getUserId, instead of getApartment().getId...) */
    @Mapping(target = "apartment", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    Reservation toDomain(ReservationDTO dto);

    ReservationBasicDTO toBasicDTO(Reservation reservation);
}