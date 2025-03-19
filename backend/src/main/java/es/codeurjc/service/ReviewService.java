package es.codeurjc.service;

import es.codeurjc.dto.NewReviewDTO;
import es.codeurjc.dto.ReviewDTO;
import es.codeurjc.dto.ReviewMapper;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Review;
import es.codeurjc.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
@Service
public class ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewDTO save(NewReviewDTO newReviewDTO) {
        Review review = ReviewMapper.toEntity(newReviewDTO);
        review = reviewRepository.save(review);
        return ReviewMapper.toDTO(review);
    }

    public Optional<ReviewDTO> findById(Long id) {
        return reviewRepository.findById(id).map(ReviewMapper::toDTO);
    }

    public List<Review> findByUser_Name(String name) {
        return reviewRepository.findByUser_Name(name);
    }

    public List<Review> findByApartment_Name(String name) {
        return reviewRepository.findByApartment_Name(name);
    }

    public List<Review> findByDate(Date date){
        return reviewRepository.findByDate(date);
    }

    public List<Review> findByScore(int score) {
        return reviewRepository.findByScore(score);
    }

    public List<Review> findByApartment(Apartment apartment) {
        return reviewRepository.findByApartment(apartment);
    }

    public List<Review> findByScoreAndApartment(int score, Apartment apartment) {
        return reviewRepository.findByScoreAndApartment(score, apartment);
    }

}
