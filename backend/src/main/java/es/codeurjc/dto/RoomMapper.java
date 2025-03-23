package es.codeurjc.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import es.codeurjc.model.Room;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface RoomMapper {

    RoomDTO toDTO(Room room);

    RoomBasicDTO toBasicDTO(Room room);

    List<RoomDTO> toDTOs(Collection<Room> rooms);

    List<RoomBasicDTO> toBasicDTOs(Collection<Room> rooms);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    Room toDomain(RoomDTO roomDTO);
}