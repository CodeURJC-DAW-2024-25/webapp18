package es.codeurjc.controllerRest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.codeurjc.dto.ApartmentDTO;
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

    @GetMapping("/")
    public Collection<ApartmentDTO> getApartments() {
        return apartmentService.getApartments();
    }

    @GetMapping("/{id}")
    public ApartmentDTO getApartment(@PathVariable Long id) {
        return apartmentService.getApartment(id);
    }

    // PENDIENTE -> Añadir los controladores de búsqueda de apartamentos y de
    // recomendación

    @PreAuthorize("hasRole('MANAGER') and principal.enabled")
    @PostMapping("/{id}")
    public ResponseEntity<?> createApartment(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody ApartmentDTO newApartmentDTO) throws IOException {

        UserE manager = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        if (manager.getId() == id) {
            newApartmentDTO = apartmentService.createApartment(newApartmentDTO, id);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newApartmentDTO.id()).toUri();
            return ResponseEntity.created(location).body(newApartmentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This apartment is not yours");
        }

    }

    @PreAuthorize("hasRole('MANAGER') and principal.enabled")
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody ApartmentDTO updatedApartmentDTO) throws IOException {

        UserE manager = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        if (!apartmentService.exist(id)){
            return ResponseEntity.notFound().build();
        } else if (manager.getId() == id) {
            return ResponseEntity.ok(apartmentService.updateApartment(updatedApartmentDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PreAuthorize("hasRole('MANAGER') and principal.enabled")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApartmentDTO> deleteApartment(HttpServletRequest request, @PathVariable Long id) {
        
        UserE manager = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        if (!apartmentService.exist(id)){
            return ResponseEntity.notFound().build();
        } else if (manager.getId() == id) {
            return ResponseEntity.ok(apartmentService.deleteApartment(id));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } 
    }

    // PENDIENTE -> Cómo se hacía con imágenes?
    @GetMapping("/{id}/images")
    public ResponseEntity<Object> downloadImage(@PathVariable Long id) throws SQLException {

        Optional<Apartment> apartment = apartmentService.findById(id);
        if (apartment.isPresent() && apartment.get().getImageFile() != null) {

            Resource file = new InputStreamResource(apartment.get().getImageFile().getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                    .contentLength(apartment.get().getImageFile().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PENDIENTE -> Hacer un modelo que tenga más detalles para este controlador, o
    // al revés para el resto
    @GetMapping("/{id}/info")
    public ResponseEntity<Apartment> apartmentInformation(@PathVariable Long id) {

        Optional<Apartment> apartmentOpt = apartmentService.findById(id);
        if (apartmentOpt.isPresent()) {
            Apartment apartment = apartmentOpt.get();
            if (apartment.getManager().getValidated()) {
                return ResponseEntity.ok(apartment);
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PENDIENTE -> Cambiar las URL de este método AJAX para que cuadren con el
    // formato

    @GetMapping("/loadMore")
    public Page<ApartmentDTO> loadMoreApartments(
            Pageable pageable,
            @RequestParam(defaultValue = "0") int numPage,
            @RequestParam(defaultValue = "6") int pageSize) {

        List<Apartment> apartments = apartmentService.findAll();
        int totalCount = apartments.size();

        if (numPage * pageSize < totalCount) {
            /*
             * int actualEnd = Math.min(numPage * pageSize, totalCount);
             * List<Apartment> paginatedApartments = apartments.subList(start, actualEnd);
             * return ResponseEntity.ok(paginatedApartments);
             */

            return apartmentService.findAll(pageable);

        } else {
            return Page.empty();
        }
    }

    @GetMapping("/manager/loadMore/{start}/{end}")
    public ResponseEntity<List<Apartment>> loadMoreApartmentsManagerView(HttpServletRequest request,
            @PathVariable int start,
            @PathVariable int end) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Apartment> allApartments = new ArrayList<>(currentUser.getApartment());
        int totalCount = allApartments.size();

        if (start < totalCount) {
            int actualEnd = Math.min(end, totalCount);
            List<Apartment> paginatedApartments = allApartments.subList(start, actualEnd);
            return ResponseEntity.ok(paginatedApartments);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
