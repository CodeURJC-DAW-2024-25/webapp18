package es.codeurjc.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


import es.codeurjc.model.Review;

@Mapper(componentModel = "spring")
@Component
public interface ReviewMapper {

    ReviewDTO toDTO(Review review);
    
    @Mapping(source = "id", target = "id")
    ReviewBasicDTO toBasicDTO(Review review);

    List<ReviewDTO> toDTOs(Collection<Review> reviews);
    List<ReviewBasicDTO> toBasicDTOs(Collection<Review> reviews);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    Review toDomain(ReviewDTO reviewDTO);

    // Pendiente -> la función de abajo es necesaria?
    //Review toBasicDomain(ReviewBasicDTO basicDTO);
}