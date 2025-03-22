package es.codeurjc.dto;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import es.codeurjc.model.UserE;

@Mapper(componentModel = "spring")

public interface UserMapper {
    @Mapping(target = "reviews", ignore = true)
    
    UserDTO toDTO(UserE user);
    
    UserBasicDTO toBasicDTO(UserE user);
    
    List<UserDTO> toDTOs(Collection<UserE> users);
    
    List<UserBasicDTO> toBasicDTOs(Collection<UserE> users);
    
    @Mapping(target = "apartment", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    UserE toDomain(UserDTO userDTO);
    
    @Mapping(target = "apartment", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    UserE toDomain(UserBasicDTO userBasicDTO);
}