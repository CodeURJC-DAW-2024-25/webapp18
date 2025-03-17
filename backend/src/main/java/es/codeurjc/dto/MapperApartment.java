package es.codeurjc.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

import es.codeurjc.dto.ApartmentDTO;
import es.codeurjc.model.Apartment;

@Mapper(componentModel = "spring")
public interface MapperApartment {
    MapperApartment INSTANCE = Mappers.getMapper(MapperApartment.class);

    @Mapping(source = "manager", target = "manager")
    @Mapping(source = "rooms", target = "rooms")
    @Mapping(source = "reservations", target = "reservations")
    @Mapping(source = "reviews", target = "reviews")
    ApartmentDTO toApartmentDTO(Apartment apartment);

    @Mapping(source = "manager", target = "manager")
    @Mapping(source = "rooms", target = "rooms")
    @Mapping(source = "reservations", target = "reservations")
    @Mapping(source = "reviews", target = "reviews")
    Apartment toApartment(ApartmentDTO apartmentDTO);
}
