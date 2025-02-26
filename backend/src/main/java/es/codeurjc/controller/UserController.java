package es.codeurjc.controller;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.Review;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.core.io.Resource;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ADVANCED RECOMMENDATION ALGORITHM
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        List<Apartment> recomendedApartments = new ArrayList<>();
        try {
            String nick = request.getUserPrincipal().getName();
            UserE user = userService.findByNick(nick).orElseThrow();
            List<Reservation> userReservations = user.getReservations();
            recomendedApartments = userService.findRecomendedApartments(6, userReservations, user);

        } catch (NullPointerException e) {

        } finally {
            if (recomendedApartments.size() < 6) {
                // size +1 to avoid looking for id = 0 if size = 0
                for (int i = recomendedApartments.size() + 1; i < 7; i++) {
                    Apartment apartment = apartmentService.findById((long) i).orElseThrow();
                    if (apartment != null && apartment.getManager().getvalidated())
                        recomendedApartments.add(apartment);
                }
            }
        }
        model.addAttribute("apartments", recomendedApartments);
        return "index";
    }

    @GetMapping("/indexsearch")
    public String indexSearch(Model model, @RequestParam String searchValue) {
        List<Apartment> apartments = apartmentService.findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(true,
                searchValue);
        model.addAttribute("apartments", apartments);
        return "index";

    }

    @GetMapping("/error")
    public String Error(Model model, HttpServletRequest request) {
        return "/error";

    }

    @GetMapping("/returnmainpage")
    public String returnmainpage(Model model, HttpServletRequest request) {
        return "redirect:/index";

    }

    // CLIENT CONTROLLERS

    // MANAGER CONTROLLERS

    /**
     * Loads the first 6 apartments of a manager
     */
    @GetMapping("/viewapartmentsmanager")
    public String viewApartmentsManager(Model model, HttpServletRequest request) {

        String managernick = request.getUserPrincipal().getName();
        UserE currentManager = userService.findByNick(managernick).orElseThrow();

        List<Apartment> apartments = currentManager.getApartment();

        if (apartments.size() > 6) {
            apartments = apartments.subList(0, 6);
        }

        model.addAttribute("apartments", apartments);

        return "viewapartmentsmanager";

    }

    @GetMapping("/testChart")
    public String testChart(Model model) {

        List<Integer> info = new ArrayList<>();
        model.addAttribute("info", info);

        return "testChart";

    }

    /**
     * Loads the data of all apartments owned by a manager to be viewed in a graph
     * 
     * @param model
     * @param request
     * @return The chart template
     */
    @GetMapping("/chartsManager")
    public String chartsManager(Model model, HttpServletRequest request) {

        String managernick = request.getUserPrincipal().getName();
        UserE currentManager = userService.findByNick(managernick).orElseThrow();

        var reviewsAverage = new ArrayList<String>();
        var apartmentNames = new ArrayList<String>();

        for (Apartment apartment : currentManager.getApartment()) {
            apartmentNames.add(apartment.getName());
            reviewsAverage.add("%.1f".formatted((apartment.getAverageRating())));
        }

        model.addAttribute("reviewsAverage", reviewsAverage);
        model.addAttribute("apartmentNames", apartmentNames);

        return "chartsManager";

    }

    @PostMapping("/application/{id}")
    public String managerApplication(Model model, HttpServletRequest request, @PathVariable Long id) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE foundManager = userService.findById(id).orElseThrow();

        if (currentUser.equals(foundManager)) {
            foundManager.setRejected(false);
            userService.save(foundManager);
            model.addAttribute("user", foundManager);
            return "redirect:/profile";

        } else
            return "/error";
    }

    // ADMIN CONTROLLERS
    @GetMapping("/chartsadmin")
    public String chartsAdmin(Model model, HttpServletRequest request) {

        return "chartsadmin";

    }

    @GetMapping("/managerValidation")
    public String managerValidation(Model model) {
        List<UserE> unvalidatedManagersList = new ArrayList<>();
        unvalidatedManagersList = userService.findByValidatedAndRejected(false, false);

        if (unvalidatedManagersList != null) {
            model.addAttribute("unvalidatedManagers", unvalidatedManagersList);
        }

        return "managerValidation";
    }

    @PostMapping("/rejection/{id}")
    public String rejectManager(Model model, @PathVariable Long id) {
        UserE manager = userService.findById(id).orElseThrow();

        if (manager != null) {
            manager.setRejected(true);
            manager.setvalidated(false);
            userService.save(manager);
        }
        return "redirect:/managervalidation";

    }

    @PostMapping("/acceptance/{id}")
    public String acceptManager(Model model, @PathVariable Long id) {
        UserE manager = userService.findById(id).orElseThrow();

        if (manager != null) {
            manager.setRejected(false);
            manager.setvalidated(true);
            userService.save(manager);
        }
        return "redirect:/managervalidation";

    }

    @GetMapping("/managerlist")
    public String managerList(Model model, HttpServletRequest request) {

        return "managerlist";

    }

    @GetMapping("/editprofile/{id}")
    public String editProfile(Model model, HttpServletRequest request, @PathVariable Long id) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE foundUser = userService.findById(id).orElseThrow(); // need to transform the throw into 404 error. Page
                                                                  // 25 // database

        if (currentUser.equals(foundUser)) {
            model.addAttribute("user", foundUser);
            return "editprofile";
        } else
            return "/error";

    }

    @PostMapping("/replace/{id}")
    public String editProfile(HttpServletRequest request, Model model, @PathVariable Long id,

            @RequestParam String name,
            @RequestParam String lastname,
            @RequestParam String location,
            @RequestParam String org,
            @RequestParam String language,
            @RequestParam String phone,
            @RequestParam String mail,
            @RequestParam String bio) { // could be changed to construct user automatically

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE foundUser = userService.findById(id).orElseThrow();

        if (currentUser.equals(foundUser)) {
            foundUser.setName(name);
            foundUser.setLocation(location);
            foundUser.setOrganization(org);
            foundUser.setLanguage(language);
            foundUser.setPhone(phone);
            foundUser.setEmail(mail);
            foundUser.setBio(bio);

            userService.save(foundUser);

            model.addAttribute("user", foundUser);

            return "redirect:/profile";
        } else
            return "/error";
    }

    @GetMapping("/profile/{id}/images")
    public ResponseEntity<Object> downloadImage(HttpServletRequest request, @PathVariable Long id) throws SQLException {

        Optional<UserE> user = userService.findById(id);
        if (user.isPresent() && user.get().getImageFile() != null) {

            Resource file = new InputStreamResource(user.get().getImageFile().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                    .contentLength(user.get().getImageFile().length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
            // return "/error";
        }
    }

    @PostMapping("/editprofileimage/{id}")
    public String editImage(HttpServletRequest request, @RequestParam MultipartFile imageFile,
            @PathVariable Long id,
            Model model) throws IOException {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE foundUser = userService.findById(id).orElseThrow();

        if (currentUser.equals(foundUser)) {
            if (!imageFile.getOriginalFilename().isBlank()) {
                currentUser.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
                userService.save(currentUser);
            }
            return "redirect:/editprofile/" + id;

        } else
            return "/error";

    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {

        String usernick = request.getUserPrincipal().getName();
        UserE currentUser = userService.findByNick(usernick).orElseThrow();
        if (currentUser.getBio() == null) {
            model.addAttribute("hasbio", false);
            currentUser.setBio("");
        } else
            model.addAttribute("hasbio", true);

        if (currentUser.getLocation() == null) {
            model.addAttribute("haslocation", false);
            currentUser.setLocation("");
        } else
            model.addAttribute("haslocation", true);

        if (currentUser.getPhone() == null) {
            model.addAttribute("hasphone", false);
            currentUser.setPhone(" ");
        } else
            model.addAttribute("hasphone", true);

        if (currentUser.getOrganization() == null) {
            model.addAttribute("hasorg", false);
            currentUser.setOrganization(" ");
        } else
            model.addAttribute("hasorg", true);

        if (currentUser.getLanguage() == null) {
            model.addAttribute("haslang", false);
            currentUser.setLanguage(" ");
        } else
            model.addAttribute("haslang", true);

        model.addAttribute("user", currentUser);
        model.addAttribute("imageFile", currentUser.getImageFile());

        return "profile";

    }

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/loginerror")
    public String loginError(Model model) {
        return "loginError";
    }

	@GetMapping("/register")
	public String registerClient(Model model) {
		return "register";
	}

	@PostMapping("/register")
	public String registerClient(Model model, UserE user, Integer type) throws IOException {
		if (!userService.existNick(user.getNick())) {
			user.setPass(passwordEncoder.encode(user.getPass()));
			List<String> rols = new ArrayList<>();
			rols.add("USER");
			if (type == 0){
				rols.add("CLIENT");
				user.setvalidated(null);
				user.setRejected(null);
			}
			else{
				rols.add("MANAGER");
				user.setvalidated(false);
				user.setRejected(false);
			}
			user.setRols(rols);
			List<Reservation> reservations= new ArrayList<>();
			user.setReservations(reservations);
			List<Apartment> apartments= new ArrayList<>();
			user.setApartment(apartments);
			List<Review> reviews= new ArrayList<>();
			user.setReviews(reviews);

			user.setLanguage("");
			user.setLocation("");
			user.setBio("");
			user.setPhone("");
			user.setOrganization("");

			userService.save(user);

			Resource image = new ClassPathResource("/static/images/default-hotel.jpg");
        	user.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        	user.setImage(true);

			userService.save(user);

			return "redirect:/login";
		} else {
			return "redirect:/nickTaken";
		}
	}

	@GetMapping("/nickTaken")
	public String takenUserName(Model model, HttpServletRequest request) {
		return "nickTaken";
	}

}
