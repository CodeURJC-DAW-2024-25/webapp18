package es.codeurjc.dto;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDTO toDTO(Review review);
    ReviewBasicDTO toBasicDTO(Review review);

    Collection<ReviewDTO> toDTOs(Collection<Review> review);
    Collection<ReviewBasicDTO> toBasicDTOs(Collection<Review> reviews);

    Review toDomain(ReviewDTO reviewDTO);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "apartment", ignore = true)
    Review toBasicDomain(ReviewBasicDTO basicDTO);

}
