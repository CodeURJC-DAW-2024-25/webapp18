package es.codeurjc.controllerRest;

import es.codeurjc.dto.ReviewDTO;
import es.codeurjc.model.Apartment;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ApartmentService apartmentService;

    // works?
    @GetMapping("/")
    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return reviewService.findAll(pageable);
    }

    // works?
    @GetMapping("/{id}")
    public ReviewDTO reviewById(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    // works?
    @GetMapping("/reviews/apartments/{id}")
    public Page<ReviewDTO> getReviewsByApartment(
            @PathVariable Long id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Pageable pageable) {

        if (page != null && size != null) {
            return reviewService.getReviewsByApartment(id, PageRequest.of(page, size));
        }

        return reviewService.getReviewsByApartment(id, pageable);
    }

    // works? Pendiente que solo pueda hacerlo un usuario logueado
    @PostMapping("reviews/apartments/{id}/")
    public ResponseEntity<ReviewDTO> createReview(HttpServletRequest request,
            @RequestBody ReviewDTO newReviewDTO,
            @PathVariable Long id) throws IOException {

        Apartment apartment = apartmentService.findById(id).orElseThrow();

        newReviewDTO = reviewService.createReview(newReviewDTO, apartment);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newReviewDTO.id()).toUri();
        return ResponseEntity.created(location).body(newReviewDTO);

    }

}