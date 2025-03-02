package es.codeurjc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Review;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReviewService;
import es.codeurjc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
	UserService userService;


	@Autowired
	ApartmentService apartmentService;


    /**
	 * This method adds to the DB the review posted by the client so it is
	 * displayed in the apartment's reviews page
	 * 
	 * @param model
	 * @param score
	 * @param comment
	 * @param date
	 * @param apartment
	 * @param user
	 * @return
	 */
	@PostMapping("/postApartmentReviews/{id}")
	public String postReview(
			Model model, HttpServletRequest request,
			@RequestParam(required = false) Integer rating,
			@RequestParam String comment,
			@PathVariable Long id) {

		UserE apartmentManager = apartmentService.findById(id).orElseThrow().getManager();

		if (apartmentManager.getvalidated()) {
			int score = (rating != null) ? rating : 0;
			if(score != 0){
				UserE user = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
				Apartment targetApartment = apartmentService.findById(id).orElseThrow();
				targetApartment.getReviews().add(new Review(score, comment, LocalDate.now(), targetApartment, user));
				apartmentService.save(targetApartment);

				return "redirect:/apartmentReviews/" + id;
			}
			else
				return "redirect:/apartmentReviews/"+id;

		} else
			return "/error";
	}

	@GetMapping("/apartmentReviews/{id}")
	public String apartmentReviews(
			Model model,
			@PathVariable Long id) {

		UserE apartmentManager = apartmentService.findById(id).orElseThrow().getManager();

		if (apartmentManager.getvalidated()) {
			Apartment selectedApartment = apartmentService.findById(id).orElseThrow();
			model.addAttribute("apartment", selectedApartment);

			List<Review> reviews = new ArrayList<>();
			for (int i = 0; i < 6 && i < selectedApartment.getReviews().size(); i++) {
				reviews.add(selectedApartment.getReviews().get(i));
			}

			model.addAttribute("apartmentReviews", reviews);
			model.addAttribute("totalreviews", selectedApartment.getReviews().size());

			for (int i = 1; i <= 5; i++) {
				reviews = reviewService.findByScoreAndApartment(i, selectedApartment);
				int numReviews = reviews.size();

				model.addAttribute("numreviews" + i, numReviews);
			}

			for (int i = 5; i >= 1; i--) {
				int percentageOfIScoreReview = selectedApartment.getPercentageOfNScore(i);

				model.addAttribute("percentageReview" + i, percentageOfIScoreReview);
			}
			return "/apartmentReviews";

		} else {
			return "/error";
		}

	}

    @GetMapping("/loadMoreReviews/{id}/{start}/{end}")
	public String loadMoreReviews(Model model,
			@PathVariable int id,
			@PathVariable int start,
			@PathVariable int end) {

		List<Review> reviews = apartmentService.findById((long) id).get().getReviews();
		int reviewsQuantity = reviews.size();

		List<Review> newReviews = new ArrayList<>();

		if (start <= reviewsQuantity) {

			for (int i = start; i < end && i <= reviewsQuantity; i++) {
				newReviews.add(reviews.get(i - 1));
			}

			model.addAttribute("apartmentReviews", newReviews);
		}

		return "apartmentReviewTemplate";
	}

}