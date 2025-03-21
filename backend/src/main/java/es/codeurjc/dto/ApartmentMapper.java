package es.codeurjc.dto;

import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import es.codeurjc.model.Apartment;

@Mapper(componentModel = "spring")
@Component
public interface ApartmentMapper {
    //PENDIENTE -> Corregir esto tras el mapper de review
    ApartmentDTO toDTO(Apartment apartment);
    ApartmentBasicDTO toBasicDTO(Apartment apartment);

    Collection<ApartmentDTO> toDTOs(Collection<Apartment> apartments);
    Collection<ApartmentBasicDTO> toBasicDTOs(Collection<Apartment> apartments);

    Apartment toDomain(ApartmentDTO apartmentDTO);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Apartment toBasicDomain(ApartmentBasicDTO basicDTO);
}