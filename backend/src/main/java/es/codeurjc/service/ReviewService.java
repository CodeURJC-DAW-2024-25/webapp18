package es.codeurjc.service;

import es.codeurjc.dto.ReviewBasicDTO;
import es.codeurjc.dto.ReviewDTO;
import es.codeurjc.dto.ReviewMapper;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Review;
import es.codeurjc.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewService implements GeneralService<Review> {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ReviewMapper mapper;

    public Collection<ReviewDTO> getReviews() {
        return toDTOs(reviewRepository.findAll());
    }

    public ReviewDTO getReview(Long id) {
        return toDTO(reviewRepository.findById(id).orElseThrow());
    }

    public ReviewDTO toDTO(Review review) {
        return mapper.toDTO(review);
    }

    ReviewBasicDTO toBasicDTO(Review review) {
        return mapper.toBasicDTO(review);
    }

    Collection<ReviewDTO> toDTOs(Collection<Review> reviews) {
        return mapper.toDTOs(reviews);
    }

    Collection<ReviewBasicDTO> toBasicDTOs(Collection<Review> reviews) {
        return mapper.toBasicDTOs(reviews);
    }

    Review toDomain(ReviewDTO reviewDTO) {
        return mapper.toDomain(reviewDTO);
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> findByUser_Name(String name) {
        return reviewRepository.findByUser_Name(name);
    }

    public List<Review> findByApartment_Name(String name) {
        return reviewRepository.findByApartment_Name(name);
    }

    public List<Review> findByDate(Date date) {
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

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findAll(Sort sort) {
        if (sort == null) {
            return reviewRepository.findAll();
        } else {
            return reviewRepository.findAll(sort);
        }
    }

    public Page<ReviewDTO> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    public Boolean exist(Long id) {
        return reviewRepository.findById(id).isPresent();
    }

    public Page<ReviewDTO> getReviewsByApartment(Long id, Pageable pageable) {
       if(apartmentService.exist(id)){
           Page<ReviewDTO> reviews = reviewRepository.findByApartment(id, pageable).map(this::toDTO);
           return reviews;
       }
       throw new NoSuchElementException();
    }



    public ReviewDTO createReview(ReviewDTO review, Apartment apartment) {

        Review newReview = toDomain(review);
        
        newReview.setApartment(apartment);
        save(newReview);
        return mapper.toDTO(newReview);
    }

}
