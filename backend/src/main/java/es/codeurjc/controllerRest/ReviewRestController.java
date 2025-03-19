package es.codeurjc.controllerRest;

import es.codeurjc.dto.NewReviewDTO;
import es.codeurjc.dto.ReviewDTO;
import es.codeurjc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/post/{id}")
    public ReviewDTO createReview(@RequestBody NewReviewDTO NewReviewDTO) {
    return reviewService.save(NewReviewDTO);
 
    }

    @GetMapping("/{id}")
    public Optional<ReviewDTO> getReview(@PathVariable Long id) {
        return reviewService.findById(id);
    }

}