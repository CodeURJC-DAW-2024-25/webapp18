package es.codeurjc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@GetMapping("/addApartment")
	public String addApartment(Model model, HttpServletRequest request) {
		Optional<UserE> user = userService.findByNick(request.getUserPrincipal().getName());
		if (user.isPresent()) {
			model.addAttribute("name", user.get().getName());
			model.addAttribute("photo", "default.jpg");
			return "addApartment";
		} else
			return "redirect:/login";
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
	

	@PostMapping("/createApartment")
	public String createApartment(HttpServletRequest request,
			@ModelAttribute("apartment") Apartment newApartment,
			BindingResult bindingResult,
			Integer room1, Integer cost1,
			Integer room2, Integer cost2,
			Integer room3, Integer cost3,
			Integer room4, Integer cost4,
			@RequestParam("imageFile") MultipartFile imageFile,
			Model model) throws IOException {

		System.out.println("Proceso de creación de apartamento iniciado");

		try {
			UserE user = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

			newApartment.setManager(user);
			newApartment.setRooms(new ArrayList<>());
			newApartment.setReservations(new ArrayList<>());
			newApartment.setReviews(new ArrayList<>());

			// Process image directly
			if (imageFile != null && !imageFile.isEmpty()) {
				newApartment.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
			} else {
				ClassPathResource defaultImage = new ClassPathResource("static/images/default.jpg");
				newApartment.setImageFile(
						BlobProxy.generateProxy(defaultImage.getInputStream(), defaultImage.contentLength()));
				newApartment.setImage(true);
			}
			newApartment.setImage(true);

			if (room1 != null && room1 > 0 && cost1 != null && cost1 > 0)
				for (int i = 0; i < room1; i++) {
					newApartment.getRooms().add(new Room(1, cost1, new ArrayList<>(), newApartment));
				}

			apartmentService.save(newApartment);

			return "redirect:/viewApartmentsManager";

		} catch (Exception e) {
			System.err.println("Error en la creación del apartamento: " + e.getMessage());
			e.printStackTrace();

			model.addAttribute("error", "Error al crear el apartamento: " + e.getMessage());
			return "addApartment";
		}
	}


	@GetMapping("/addApartmentPhoto/{imgName}")
	public String addApartmentPhoto(Model model, HttpServletRequest request, @PathVariable String imgName) {
		model.addAttribute("photo", imgName);
		return "addApartmentPhoto";
	}

	
	@PostMapping("/editApartmentimage/{id}")
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
			return "redirect:/editApartment/" + id;
		} else
			return "/error";
	}

	@PostMapping("/selectApartmentimage/{imgName}")
	public String selectImage(@RequestParam MultipartFile imageFile,
			Model model, HttpServletRequest request, @PathVariable String imgName) throws IOException {

		if (!imageFile.getOriginalFilename().isBlank())
			return "redirect:/addApartment/" + imageFile.getOriginalFilename();
		else
			return "redirect:/addApartmentPhoto/" + imgName;
	}




	@GetMapping("/editApartment/{id}")
	public String editApartment(@PathVariable Long id, Model model) {
		Optional<Apartment> apartmentOpt = apartmentService.findById(id);
		
		if (apartmentOpt.isPresent()) {
			Apartment apartment = apartmentOpt.get();
			model.addAttribute("apartment", apartment);
			model.addAttribute("id", id);
			
			int[] roomCounts = new int[5];
			float[] roomCosts = new float[5]; 
			
			for (int i = 1; i <= 4; i++) {
				roomCounts[i] = 0;
				roomCosts[i] = 0;
			}
			
			// Count rooms and add cost
			for (Room room : apartment.getRooms()) {
				int maxClients = room.getMaxClients();
				if (maxClients >= 1 && maxClients <= 4) {
					roomCounts[maxClients]++;
					if (roomCounts[maxClients] == 1) {
						roomCosts[maxClients] = room.getcost(); 
					}
				}
			}
			
			
			for (int i = 1; i <= 4; i++) {
				model.addAttribute("room" + i, roomCounts[i]);
				model.addAttribute("cost" + i, (int)roomCosts[i]); 
			}
			
			System.out.println("Editando apartamento ID: " + id + ", Nombre: " + apartment.getName());
			
			return "editApartment";
		} else {
			System.out.println("No se encontró apartamento con ID: " + id);
			return "redirect:/viewApartmentsManager";
		}
	}
	
	@PostMapping("/updateApartment/{id}")
	public String updateApartment(HttpServletRequest request,
			@PathVariable Long id,
			@ModelAttribute("apartment") Apartment updatedApartment,
			Integer room1, Integer cost1,
			Integer room2, Integer cost2,
			Integer room3, Integer cost3,
			Integer room4, Integer cost4,
			@RequestParam(value = "imageFileUpload", required = false) MultipartFile imageFile,
			Model model) {
		
		try {
			Optional<Apartment> existingApartmentOpt = apartmentService.findById(id);
			if (!existingApartmentOpt.isPresent()) {
				model.addAttribute("error", "Apartamento no encontrado");
				return "editApartment";
			}
			
			Apartment existingApartment = existingApartmentOpt.get();
			
			existingApartment.setName(updatedApartment.getName());
			existingApartment.setLocation(updatedApartment.getLocation());
			existingApartment.setDescription(updatedApartment.getDescription());
			
			if (imageFile != null && !imageFile.isEmpty()) {
				existingApartment.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
				existingApartment.setImage(true);
			}
			
			existingApartment.getRooms().clear();
			
			if (room1 != null && room1 > 0 && cost1 != null && cost1 > 0) {
				for (int i = 0; i < room1; i++) {
					existingApartment.getRooms().add(new Room(1, cost1, new ArrayList<>(), existingApartment));
				}
			}
			
			if (room2 != null && room2 > 0 && cost2 != null && cost2 > 0) {
				for (int i = 0; i < room2; i++) {
					existingApartment.getRooms().add(new Room(2, cost2, new ArrayList<>(), existingApartment));
				}
			}
			
			if (room3 != null && room3 > 0 && cost3 != null && cost3 > 0) {
				for (int i = 0; i < room3; i++) {
					existingApartment.getRooms().add(new Room(3, cost3, new ArrayList<>(), existingApartment));
				}
			}
			
			if (room4 != null && room4 > 0 && cost4 != null && cost4 > 0) {
				for (int i = 0; i < room4; i++) {
					existingApartment.getRooms().add(new Room(4, cost4, new ArrayList<>(), existingApartment));
				}
			}
			
			apartmentService.save(existingApartment);
			
			return "redirect:/viewApartmentsManager";
			
		} catch (Exception e) {
			model.addAttribute("error", "Error al actualizar el apartamento: " + e.getMessage());
			return "editApartment";
		}
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

				return "redirect:/viewApartmentsManager";

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

		@GetMapping("/apartmentInformation/{id}")
		public String apartmentInformation(Model model, @PathVariable Long id) {

			UserE apartmentManager = apartmentService.findById(id).orElseThrow().getManager();

			if (apartmentManager.getvalidated()) {
				Apartment apartment = apartmentService.findById(id).orElseThrow();
				if (apartment.getManager().getvalidated() == false)
					return "redirect:/error";
				model.addAttribute("apartment", apartment);
				model.addAttribute("numRooms", apartment.getNumRooms());

				return "/apartmentInformation";

			} else {
				return "/error";
			}

		}

/* 	@GetMapping("/clientlist/{id}")
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
	} */

	/**
	 * Using AJAX, loads the next 6 apartments in the page, or none if all are
	 * loaded
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
        var apartments = new ArrayList<Apartment>();
        
        // We obtain the apartments IDs for the actual page
        List<Long> apartmentIds = new ArrayList<>();
        for (long index = start; index < end && index <= apartmentsQuantity; index++) {
            apartmentIds.add(index);
        }
        
        // We look for the avalidated apt
        for (Long apartmentId : apartmentIds) {
            Apartment apartment = apartmentService.findById(apartmentId).orElse(null);
            if (apartment != null && apartment.getManager().getvalidated()) {
                apartments.add(apartment);
            }
        }
        
        model.addAttribute("apartments", apartments);
    }
    
    return "apartmentTemplate";
}

	/**
	 * Using AJAX, loads the next 6 apartments in the page, or none if all are
	 * loaded
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

		var apartmentsQuantity = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow()
				.getApartment().size();

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

		return "viewApartmentTemplate";
	}

}
