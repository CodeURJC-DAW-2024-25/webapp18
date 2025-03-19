package es.codeurjc.dto;

import java.util.Collection;
import org.mapstruct.Mapper;
import es.codeurjc.model.Review;


@Mapper(componentModel = "spring")
public interface ReviewMapper {

    //PENDIENTE -> Corregir esto tras el mapper de review
    ReviewDTO toDTO(Review review);
    ReviewBasicDTO toBasicDTO(Review review);

    Collection<ReviewDTO> toDTOs(Collection<Review> reviews);
    Collection<ReviewBasicDTO> toBasicDTOs(Collection<Review> reviews);

    Review toDomain(ReviewDTO reviewDTO);
    Review toBasicDomain(ReviewBasicDTO basicDTO);
}
