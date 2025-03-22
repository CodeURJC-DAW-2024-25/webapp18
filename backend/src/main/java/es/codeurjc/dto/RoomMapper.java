package es.codeurjc.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import es.codeurjc.model.Room;

@Mapper(componentModel = "spring")

public interface RoomMapper {
    
    @Mapping(target = "apartment", ignore = true)
    RoomDTO toDTO(Room room);
 
    RoomBasicDTO toBasicDTO(Room room);
    
    List<RoomDTO> toDTOs(Collection<Room> rooms);
    
    List<RoomBasicDTO> toBasicDTOs(Collection<Room> rooms);
    
    @Mapping(target = "apartment.manager", ignore = true)
    @Mapping(target = "apartment.rooms", ignore = true) 
    @Mapping(target = "apartment.reservations", ignore = true)
    @Mapping(target = "apartment.reviews", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Room toDomain(RoomDTO roomDTO);
}