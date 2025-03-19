package es.codeurjc.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.model.UserE;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(UserE user);

    UserBasicDTO toBasicDTO(UserE user);

    List<UserDTO> toDTOs(Collection<UserE> users);

    List<UserBasicDTO> toBasicDTOs(Collection<UserE> users);

    @Mapping(target = "reservations", ignore = true)
    UserE toDomain(UserDTO userDTO);

    @Mapping(target = "users", ignore = true)
    UserE toDomain(UserBasicDTO userBasicDTO);
}
