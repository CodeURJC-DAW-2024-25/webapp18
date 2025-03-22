package es.codeurjc.controllerRest;


import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.codeurjc.dto.ReviewDTO;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Review;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;
 
    @Autowired
    private ApartmentService apartmentService;
 
    @PostMapping("/post/{id}")
    public ResponseEntity<ReviewDTO> postReview(HttpServletRequest request,
                                                @RequestBody Review newReview,
                                                @PathVariable Long id,
                                                @PathVariable String comment,
                                                @PathVariable int score,
                                                @PathVariable UserE user) throws IOException {
 
        Optional<Apartment> optionalApartment = apartmentService.findById(id);
        if (optionalApartment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
 
        Apartment apartment = optionalApartment.get();

        newReview.setScore(score);
        newReview.setComment(comment);
        newReview.setDate(LocalDate.now());
        newReview.setApartment(apartment);
        newReview.setUser(user);
        reviewService.save(newReview);

        URI location = fromCurrentRequest().path("/post/{id}")
                .buildAndExpand(newReview.getId()).toUri();

        return ResponseEntity.created(location).body(reviewService.toDTO(newReview));

        }
    

 
    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable Long id) {
       return reviewService.getReview(id);

}


    @GetMapping("/reviews/loadMore/{start}/{end}")
    public ResponseEntity<List<Review>> loadMoreReviews(HttpServletRequest request,
            @RequestParam Long id,
            @PathVariable int start,
            @PathVariable int end) {

        Apartment currentAppartment = apartmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        List<Review> allReviews = new ArrayList<>();
        for (Review review : currentAppartment.getReviews()) {
            allReviews.add(review);
        }
        int totalCount = allReviews.size();
    
        if (start < totalCount) {
            int actualEnd = Math.min(end, totalCount);
            List<Review> paginatedReviews = allReviews.subList(start, actualEnd);
            return ResponseEntity.ok(paginatedReviews);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}