package es.codeurjc.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.dto.ReviewBasicDTO;
import es.codeurjc.dto.NewReviewDTO;
import es.codeurjc.model.Review;

public class ReviewMapper {

    public static Review toEntity(NewReviewDTO dto) {
    Review review = new Review();
    review.setIdReview(review.getIdReview());
    review.setUser(review.getUser().getName());
    review.setApartmentId(review.getApartmentId().getName());
    review.setScore(review.getScore());
    review.setDate(review.getDate());
    review.setComment(review.getComment());
    return review;
    }

    public static ReviewDTO toDTO(Review review) {
        Review dto = new Review();
        dto.setIdReview(review.getIdReview());
        dto.setUser(review.getUser().getName());
        dto.setApartment(review.getApartment().getName());
        dto.setScore(review.getScore());
        dto.setDate(review.getDate());
        dto.setComment(review.getComment());
        return dto;
    }
}
