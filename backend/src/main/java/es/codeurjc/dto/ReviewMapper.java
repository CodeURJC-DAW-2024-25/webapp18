package es.codeurjc.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import es.codeurjc.model.Review;

@Mapper(componentModel = "spring")

public interface ReviewMapper {

    //PENDIENTE -> Corregir esto tras el mapper de review
    @Mapping(source = "id", target = "reviewId")
    ReviewDTO toDTO(Review review);
    
    @Mapping(source = "id", target = "reviewId")
    ReviewBasicDTO toBasicDTO(Review review);

    List<ReviewDTO> toDTOs(Collection<Review> reviews);
    List<ReviewBasicDTO> toBasicDTOs(Collection<Review> reviews);

    @Mapping(source = "reviewId", target = "id")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    Review toDomain(ReviewDTO reviewDTO);
    
    @Mapping(source = "reviewId", target = "id")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    Review toBasicDomain(ReviewBasicDTO basicDTO);
}