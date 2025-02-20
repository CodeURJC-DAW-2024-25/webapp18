package es.codeurjc.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.backend.model.Review;
import es.codeurjc.backend.model.Apartment;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByUser_Name(String name);

    List<Review> findByHotel_Name(String name);

    List<Review> findByDate(Date date);

    List<Review> findByScore(int score);

    List<Review> findByHotel(Apartment apartment);

    List<Review> findByScoreAndHotel(int score, Apartment apartment);

}
