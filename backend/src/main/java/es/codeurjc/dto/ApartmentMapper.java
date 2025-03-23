package es.codeurjc.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import es.codeurjc.model.Apartment;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
@Component
public interface ApartmentMapper {


    ApartmentDTO toDTO(Apartment apartment);


    ApartmentBasicDTO toBasicDTO(Apartment apartment);

    List<ApartmentDTO> toDTOs(List<Apartment> apartments);

    List<ApartmentBasicDTO> toBasicDTOs(List<Apartment> apartments);
    
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "manager", ignore = true)
    Apartment toDomain(ApartmentDTO apartmentDTO);

}