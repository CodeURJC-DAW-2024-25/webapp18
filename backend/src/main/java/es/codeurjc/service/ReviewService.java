package es.codeurjc.service;



import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Review;
import es.codeurjc.repository.ReviewRepository;

@Service
public class ReviewService implements GeneralService<Review> {

    @Autowired
    private ReviewRepository reviewRepository;


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

    public List<Review> findByUser_Name(String name){
        return reviewRepository.findByUser_Name(name);
    }

    public List<Review> findByApartment_Name(String name){
        return reviewRepository.findByApartment_Name(name);
    }

    public List<Review> findByDate(Date date){
        return reviewRepository.findByDate(date);
    }

    public List<Review> findByScore(int score){
        return reviewRepository.findByScore(score);
    }

    public List<Review> findByApartment(Apartment apartment){
        return reviewRepository.findByApartment(apartment);
    }

    public List<Review> findByScoreAndApartment(int score, Apartment apartment){
        return reviewRepository.findByScoreAndApartment(score, apartment);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findAll(Sort sort) {
        if (!sort.equals(null)) {
            return reviewRepository.findAll(sort);
        } else {
            return reviewRepository.findAll();
        }
    }


    @Override
    public Boolean exist(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent())
            return true;
        else
            return false;
    }
}
