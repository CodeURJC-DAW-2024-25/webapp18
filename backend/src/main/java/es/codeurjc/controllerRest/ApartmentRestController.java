package es.codeurjc.controllerRest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.codeurjc.dto.ApartmentDTO;
import es.codeurjc.dto.ApartmentMapper;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReviewService;
import es.codeurjc.service.RoomService;
import es.codeurjc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/apartments")
public class ApartmentRestController {

    @Autowired
    UserService userService;
    @Autowired
    ApartmentService apartmentService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    RoomService roomService;

    @Autowired
    ApartmentMapper mapper;

    // works
    // PENDIENTE -> Should we control if page and size are off-limits? Right now it
    // returns an empty page
    @GetMapping("/")
    public Page<ApartmentDTO> getApartments(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Pageable pageable) {

        if (page != null && size != null) {
            return apartmentService.findAll(PageRequest.of(page, size));
        }
        return apartmentService.findAll(pageable);
    }

    // works? Pendiente SHould only work if the user is logged
    @GetMapping("/yourApartments")
    public Page<ApartmentDTO> getYourApartments(HttpServletRequest request,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Pageable pageable) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        if (page != null && size != null) {
            return apartmentService.findByManager(currentUser, PageRequest.of(page, size));
        }
        return apartmentService.findByManager(currentUser, pageable);
    }


    // works
    @GetMapping("/{id}")
    public ApartmentDTO getApartment(@PathVariable Long id) {
        return apartmentService.getApartment(id);
    }

    // works?
    @GetMapping("/recommended")
    public List<ApartmentDTO> getRecommendedApartments(HttpServletRequest request) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        return mapper.toDTOs(userService.findRecommendedApartments(6, currentUser.getReservations(), currentUser));
    }


    // works?
    @GetMapping("/search")
    public List<ApartmentDTO> searchApartments(@RequestParam String name) {
        return apartmentService.findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(name);
    }


    // works?
    @GetMapping("/{id}/ratings")
    public Map<Integer, Float> getApartmentRatings(@PathVariable Long id) {

        Apartment apartment = apartmentService.findById(id).orElseThrow();
        return apartmentService.getPercentageOfScores(apartment);
    }


    // works pendiente -> it must auth the user as manager
    @PostMapping("/")
    public ResponseEntity<ApartmentDTO> createApartment(
            @RequestBody ApartmentDTO newApartmentDTO) throws IOException {

        newApartmentDTO = apartmentService.createApartment(newApartmentDTO);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newApartmentDTO.id()).toUri();
        return ResponseEntity.created(location).body(newApartmentDTO);

    }

    // pendiente -> it must auth the user as manager
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(
            /* HttpServletRequest request, */
            @PathVariable Long id,
            @RequestBody ApartmentDTO updatedApartmentDTO) throws IOException {

        /*
         * UserE manager =
         * userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
         */

        if (!apartmentService.exist(id)) {
            throw new NoSuchElementException();

            /* } else if (manager.getId() == id) { */
        } else {
            return ResponseEntity.ok(apartmentService.updateApartment(updatedApartmentDTO, id));
            /*
             * } else {
             * return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
             */
        }
    }

    // works?
    // pendiente -> it must auth the user as manager
    @DeleteMapping("/{id}")
    public ResponseEntity<ApartmentDTO> deleteApartment(/* HttpServletRequest request, */@PathVariable Long id) {

        /*
         * UserE manager =
         * userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
         */

        if (!apartmentService.exist(id)) {
            return ResponseEntity.notFound().build();
        } else /* if user is manager */ {
            return ResponseEntity.ok(apartmentService.deleteApartment(id));
        } /*
           * else {
           * return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
           * }
           */
    }

    // Image controllers

    // works?
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getApartmentImage(@PathVariable Long id) throws SQLException, IOException {

        Resource apartmentImage = apartmentService.getImageFile(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg").body(apartmentImage);

    }

    // works?
    // Pendiente -> only the manager of the apartment
    @PostMapping("/{id}/image")
    public ResponseEntity<Object> createApartmentImage(
            @PathVariable Long id,
            @RequestParam MultipartFile imageFile) throws IOException {

        URI location = fromCurrentRequest().build().toUri();
        apartmentService.createImage(id, location, imageFile.getInputStream(), imageFile.getSize());
        return ResponseEntity.created(location).build();
    }

    // works? pendiente -> only the manager of the apartment
    @PutMapping("/{id}/image")
    public ResponseEntity<Object> editApartmentImage(
            @PathVariable Long id,
            @RequestParam MultipartFile imageFile) throws IOException {
        apartmentService.editImage(id, imageFile.getInputStream(), imageFile.getSize());
        return ResponseEntity.noContent().build();
    }

    // works? pendiente -> only the manager of the apartment
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deleteApartmentImage(@PathVariable Long id) throws IOException {
        apartmentService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
  

}