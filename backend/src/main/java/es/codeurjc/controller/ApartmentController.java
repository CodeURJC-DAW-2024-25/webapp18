package es.codeurjc.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Room;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReviewService;
import es.codeurjc.service.RoomService;
import es.codeurjc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ApartmentController {

	@Autowired
	UserService userService;
	
	@Autowired
	ApartmentService apartmentService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	RoomService roomService;

	@GetMapping("/editapartment/{id}")
	public String editapartment(HttpServletRequest request, Model model, @PathVariable Long id) {

		UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
		UserE foundUser = apartmentService.findById(id).orElseThrow().getManager();

		if (currentUser.equals(foundUser)) {
			Apartment apartment = apartmentService.findById(id).orElseThrow();
			model.addAttribute("apartment", apartment);
			return "editApartment";

		} else
			return "/error";
	}

	@PostMapping("/editapartment/{id}")
	public String editapartment(Model model, HttpServletRequest request, Apartment newApartment, Integer room1, Integer cost1,
			Integer room2,
			Integer cost2, Integer room3, Integer cost3, Integer room4, Integer cost4, @PathVariable Long id)
			throws IOException {

		UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
		UserE foundUser = apartmentService.findById(id).orElseThrow().getManager();

		if (currentUser.equals(foundUser)) {
			Apartment apartment = apartmentService.findById(id).orElseThrow();

			apartment.setName(newApartment.getName());
			apartment.setLocation(newApartment.getLocation());
			apartment.setDescription(newApartment.getDescription());


			if (room1 != null)
				for (int i = 0; i < room1; i++) {
					Room room = new Room(1, cost1, new ArrayList<>(), newApartment);
					apartment.getRooms().add(room);
					roomService.save(room);
				}

			if (room2 != null)
				for (int i = 0; i < room2; i++) {
					Room room = new Room(2, cost2, new ArrayList<>(), newApartment);
					apartment.getRooms().add(room);
					roomService.save(room);
				}

			if (room3 != null)
				for (int i = 0; i < room3; i++) {
					Room room = new Room(3, cost3, new ArrayList<>(), newApartment);
					apartment.getRooms().add(room);
					roomService.save(room);
				}

			if (room4 != null)
				for (int i = 0; i < room4; i++) {
					Room room = new Room(4, cost4, new ArrayList<>(), newApartment);
					apartment.getRooms().add(room);
					roomService.save(room);
				}

			apartmentService.save(apartment);

			model.addAttribute("apartment", apartment);

			return "redirect:/viewapartmentsmanager";

		} else
			return "/error";

	}

	@GetMapping("/deleteApartment/{id}")
	public String deleteApartment(HttpServletRequest request, Model model, @PathVariable Long id) {

		UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
		UserE foundUser = apartmentService.findById(id).orElseThrow().getManager();

		if (currentUser.equals(foundUser)) {
			Optional<Apartment> apartment = apartmentService.findById(id);
			if (apartment.isPresent()) {
				apartmentService.deleteById(id);
			}

			return "redirect:/viewapartmentsmanager";

		} else
			return "/error";
	}

	@GetMapping("/index/{id}/images")
	public ResponseEntity<Object> downloadImage(HttpServletRequest request, @PathVariable Long id) throws SQLException {

		Optional<Apartment> apartment = apartmentService.findById(id);
		if (apartment.isPresent() && apartment.get().getImageFile() != null) {

			Resource file = new InputStreamResource(apartment.get().getImageFile().getBinaryStream());
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
					.contentLength(apartment.get().getImageFile().length()).body(file);

		} else {
			return ResponseEntity.notFound().build();
			// return "/error";
		}
	}

	@PostMapping("/editapartmentimage/{id}")
	public String editImage(HttpServletRequest request, @RequestParam MultipartFile imageFile,
			@PathVariable Long id,
			Model model) throws IOException {

		UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
		UserE foundUser = apartmentService.findById(id).orElseThrow().getManager();

		if (currentUser.equals(foundUser)) {
			Apartment apartment = apartmentService.findById(id).orElseThrow();

			if (!imageFile.getOriginalFilename().isBlank()) {
				apartment.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
				apartmentService.save(apartment);
			}
			model.addAttribute("apartment", apartment);
			return "redirect:/editapartment/" + id;

		} else
			return "/error";
	}

	@PostMapping("/selectapartmentimage/{imgName}")
	public String editImage(@RequestParam MultipartFile imageFile,
			Model model, HttpServletRequest request, @PathVariable String imgName) throws IOException {

		if (!imageFile.getOriginalFilename().isBlank())
			return "redirect:/addApartment/" + imageFile.getOriginalFilename();
		else
			return "redirect:/addApartmentPhoto/" + imgName;
	}

	@GetMapping("/apartmentInformation/{id}")
	public String apartmentInformation(Model model, @PathVariable Long id) {

		UserE apartmentManager = apartmentService.findById(id).orElseThrow().getManager();

		if (apartmentManager.getvalidated()) {
			Apartment apartment = apartmentService.findById(id).orElseThrow();
			if (apartment.getManager().getvalidated() == false)
				return "redirect:/error";
			model.addAttribute("apartment", apartment);
			model.addAttribute("numRooms", apartment.getNumRooms());

			return "apartmentInformation";

		} else
			return "/error";

	}

	

	@GetMapping("/addApartment/{imgName}")
	public String addApartmentWithPhoto(Model model, HttpServletRequest request, @PathVariable String imgName) {

		Optional<UserE> user = userService.findByNick(request.getUserPrincipal().getName());
		if (user.isPresent()) {
			model.addAttribute("name", user.get().getName());
			model.addAttribute("photo", imgName);
			return "addApartment";

		} else
			return "redirect:/login";

	}

	@PostMapping("/createApartment/{imgName}")
	public String addApartmentPost(HttpServletRequest request, Apartment newApartment, Integer room1, Integer cost1, Integer room2,
			Integer cost2, Integer room3, Integer cost3, Integer room4, Integer cost4, @PathVariable String imgName)
			throws IOException {

		UserE user = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

		newApartment.setManager(user);
		newApartment.setRooms(new ArrayList<>());
		newApartment.setReservations(new ArrayList<>());
		newApartment.setReviews(new ArrayList<>());

		Resource image = new ClassPathResource("/static/images/" + imgName);

		newApartment.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
		newApartment.setImage(true);

		if (room1 != null)
			for (int i = 0; i < room1; i++) {
				newApartment.getRooms().add(new Room(1, cost1, new ArrayList<>(), newApartment));
			}

		if (room2 != null)
			for (int i = 0; i < room2; i++) {
				newApartment.getRooms().add(new Room(2, cost2, new ArrayList<>(), newApartment));
			}

		if (room3 != null)
			for (int i = 0; i < room3; i++) {
				newApartment.getRooms().add(new Room(3, cost3, new ArrayList<>(), newApartment));
			}

		if (room4 != null)
			for (int i = 0; i < room4; i++) {
				newApartment.getRooms().add(new Room(4, cost4, new ArrayList<>(), newApartment));
			}
		apartmentService.save(newApartment);
		return "redirect:/viewapartmentsmanager";
	}

	@GetMapping("/addApartmentPhoto/{imgName}")
	public String addApartmentPost(Model model, HttpServletRequest request, @PathVariable String imgName) {
		model.addAttribute("photo", imgName);
		return "addApartmentPhoto";
	}

	@GetMapping("/clientlist/{id}")
	public String clientlist(Model model, HttpServletRequest request, @PathVariable Long id) {

		UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
		UserE foundUser = apartmentService.findById(id).orElseThrow().getManager();

		if (currentUser.equals(foundUser)) {
			Apartment apartment = apartmentService.findById(id).orElseThrow();
			List<UserE> validClients = new ArrayList<>();
			validClients = apartmentService.getValidClients(apartment);
			model.addAttribute("clients", validClients);

			return "clientlist";

		} else
			return "/error";
	}

	/**
	 * Loads the next 6 apartments
	 */
	/*
	 * @GetMapping("/loadMoreApartments/{start}/{end}")
	 * public String loadMoreApartments(
	 * Model model,
	 * 
	 * @PathVariable Long start,
	 * 
	 * @PathVariable Long end) {
	 * 
	 * var apartmentsQuantity = apartmentService.count();
	 * 
	 * if (start <= apartmentsQuantity) {
	 * 
	 * var apartments = new ArrayList<>();
	 * 
	 * // we get the next 6 apartments or the remaining ones
	 * for (Long index = start; index < end && index < apartmentsQuantity; index++) {
	 * apartments.add(apartmentService.findById(index));
	 * }
	 * 
	 * model.addAttribute("apartments", apartments);
	 * }
	 * 
	 * return "/apartmentTemplate";
	 * }
	 */

	/**
	 * Using AJAX, loads the next 6 apartments in the page, or none if all are loaded
	 * 
	 * @param model
	 * @param start
	 * @param end
	 * @return
	 */
	@GetMapping("/loadMoreApartments/{start}/{end}")
	public String loadMoreApartments(Model model,
			@PathVariable Long start,
			@PathVariable Long end) {

		var apartmentsQuantity = apartmentService.count();

		if (start <= apartmentsQuantity) {

			var apartments = new ArrayList<>();

			// We obtain the apartments IDs for the actual page
			List<Long> apartmentIds = new ArrayList<>();
			for (long index = start; index < end && index <= apartmentsQuantity; index++) {
				apartmentIds.add(index);
			}

			// We look for the Apartment objects related to the IDs
			for (Long apartmentId : apartmentIds) {
				Apartment apartment = apartmentService.findById(apartmentId).orElse(null);
				if (apartment != null) {
					apartments.add(apartment);
				}
			}

			model.addAttribute("apartments", apartments);
		}

		return "apartmentTemplate";
	}

	

	/**
	 * Using AJAX, loads the next 6 apartments in the page, or none if all are loaded
	 * 
	 * @param model
	 * @param start
	 * @param end
	 * @return
	 */
	@GetMapping("/loadMoreApartmentsManagerView/{start}/{end}")
	public String loadMoreApartmentsManagerView(
		Model model,
		HttpServletRequest request,
			@PathVariable Long start,
			@PathVariable Long end) {


		var apartmentsQuantity = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow().getApartment().size();

		if (start <= apartmentsQuantity) {

			var apartments = new ArrayList<>();

			// We obtain the apartments IDs for the actual page
			List<Long> apartmentIds = new ArrayList<>();
			for (long index = start; index < end && index <= apartmentsQuantity; index++) {
				apartmentIds.add(index);
			}

			// We look for the Apartment objects related to the IDs
			for (Long apartmentId : apartmentIds) {
				Apartment apartment = apartmentService.findById(apartmentId).orElse(null);
				if (apartment != null) {
					apartments.add(apartment);
				}
			}

			model.addAttribute("apartments", apartments);
		}

		return "apartmentListViewTemplate";
	}

}
