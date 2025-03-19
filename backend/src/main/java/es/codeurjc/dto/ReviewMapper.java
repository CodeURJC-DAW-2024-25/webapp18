package es.codeurjc.dto;

import es.codeurjc.model.Review;

public class ReviewMapper {

    public static Review toEntity(NewReviewDTO dto) {
        Review review = new Review();
        review.setScore(dto.getScore());
        review.setComment(dto.getComment());
        review.setUserId(dto.getUserId());
        review.setApartmentId(dto.getApartmentId());
        return review;
    }

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO(); // Ahora es un ReviewDTO
        dto.setReviewId(review.getReviewId());
        dto.setUserId(review.getUserId());
        dto.setApartmentId(review.getApartmentId());
        dto.setScore(review.getScore());
        dto.setDate(review.getDate());
        dto.setComment(review.getComment());
        return dto;
    }
}
