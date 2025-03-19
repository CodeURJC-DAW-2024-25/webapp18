package es.codeurjc.dto;

import org.mapstruct.Mapper;

import es.codeurjc.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    
} ReviewMapper {

    public static Review toEntity(NewReviewDTO dto) {
        Review review = new Review();
        review.setScore(dto.getScore());
        review.setComment(dto.getComment());
        review.setUser(dto.getUser());
        review.setApartment(dto.getApartment());
        return review;
    }

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO(); // Ahora es un ReviewDTO
        dto.setReviewId(review.getReviewId());
        dto.setUser(review.getUser());
        dto.setApartment(review.getApartment());
        dto.setScore(review.getScore());
        dto.setDate(review.getDate());
        dto.setComment(review.getComment());
        return dto;
    }
}
