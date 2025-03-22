package es.codeurjc.dto;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.codeurjc.model.Apartment;


@Mapper(componentModel = "spring", 
        uses = {RoomMapper.class, ReservationMapper.class, ReviewMapper.class, UserMapper.class})
public interface ApartmentMapper {
    @Mappings({
        @Mapping(target = "reviews", ignore = true),
        @Mapping(target = "manager", ignore = true),
        @Mapping(target = "rooms", ignore = true),
        @Mapping(target = "reservations", ignore = true)
    })
    ApartmentDTO toDTO(Apartment apartment);

    @Mapping(target = "name", ignore = true)
    ApartmentBasicDTO toBasicDTO(Apartment apartment);

    List<ApartmentDTO> toDTOs(Collection<Apartment> apartments);
    
    List<ApartmentBasicDTO> toBasicDTOs(Collection<Apartment> apartments);

    @Mappings({
        @Mapping(target = "reviews", ignore = true),
        @Mapping(target = "manager", ignore = true),
        @Mapping(target = "rooms", ignore = true),
        @Mapping(target = "reservations", ignore = true)
    })
    Apartment toDomain(ApartmentDTO apartmentDTO);

    @Mappings({
        @Mapping(target = "rooms", ignore = true),
        @Mapping(target = "reservations", ignore = true),
        @Mapping(target = "reviews", ignore = true),
        @Mapping(target = "manager", ignore = true)
    })
    Apartment toBasicDomain(ApartmentBasicDTO basicDTO);
}