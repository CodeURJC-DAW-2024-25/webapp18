package es.codeurjc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Review;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReviewService;
import es.codeurjc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/post/{id}")
    public ReviewDTO createReview(@RequestBody NewReviewDTO NewReviewDTO) {
    return reviewService.save(newReviewDTO);
 
    }

    @GetMapping("/{id}")
    public Optional<ReviewDTO> getReview(@PathVariable Long id) {
        return reviewService.findById(id);
    }

}