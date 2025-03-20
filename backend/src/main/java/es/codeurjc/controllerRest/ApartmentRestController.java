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
@RequestMapping("/api/apartments")
public class ApartmentRestController{
    
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
/*         Optional<Apartment> apartment = apartmentService.findById(id);
        if (apartment.isPresent()) {
            return ResponseEntity.ok(apartment.get());
        } else {
            return ResponseEntity.notFound().build();
        } */
       return apartmentService.getApartment(id);
    }

    //PENDIENTE -> Añadir los controladores de búsqueda de apartamentos y de recomendación

    @PreAuthorize("hasRole('MANAGER') and principal.enabled")
    @PostMapping("/")
    public ResponseEntity<ApartmentDTO> createApartment(HttpServletRequest request,
                                                  @RequestBody Apartment newApartment) throws IOException {

        try {
            UserE user = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

            newApartment.setManager(user);
            newApartment.setRooms(new ArrayList<>());
            newApartment.setReservations(new ArrayList<>());
            newApartment.setReviews(new ArrayList<>());
            apartmentService.save(newApartment);

            URI location = fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newApartment.getId()).toUri();

            return ResponseEntity.created(location).body(apartmentService.toDTO(newApartment));

        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(null);

        }
    }

    @PreAuthorize("hasRole('MANAGER') and principal.enabled")
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(
        HttpServletRequest request,
        @PathVariable Long id,
        @RequestBody ApartmentDTO updatedApartmentDTO,
        @RequestParam(value = "imageFileUpload", required = false)
        MultipartFile imageFile) throws IOException {

        try {
            Optional<Apartment> oldApartmentOpt = apartmentService.findById(id);
            if (!oldApartmentOpt.isPresent()) {
                throw new NoSuchElementException();
            }

            Apartment oldApartment = oldApartmentOpt.get();

            // PENDIENTE -> Ver si se puede hacer con un mapper u otro método de clonado de objetos
            oldApartment = apartmentService.toDomain(updatedApartmentDTO);

            oldApartment.setName(updatedApartmentDTO.name());
            oldApartment.setLocation(updatedApartmentDTO.location());
            oldApartment.setDescription(updatedApartmentDTO.description());

            if (imageFile != null && !imageFile.isEmpty()) {
                oldApartment
                        .setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
                oldApartment.setImage(true);
            }

            apartmentService.save(oldApartment);

            return ResponseEntity.ok(apartmentService.toDTO(oldApartment));

        } catch (Exception e) {
            // PENDIENTE -> Revisar que este error se trate correctamente de esta manera o hay que cambiarlo / quitarlo
            return ResponseEntity.status(500).body(null);
        }
    }

    @PreAuthorize("hasRole('MANAGER') and principal.enabled")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApartmentDTO> deleteApartment(HttpServletRequest request, @PathVariable Long id) {

        try {
            UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
            UserE apartmentManager = apartmentService.findById(id).orElseThrow().getManager();
           /*  Optional<Apartment> apartment = apartmentService.findById(id);

            if (currentUser.equals(apartmentManager)) {
                if (apartment.isPresent()) {
                    apartmentService.deleteById(id);
                    return ResponseEntity.ok(apartment.get());
                }else {
                    return ResponseEntity.notFound().build(); 
                }
            } else {
                return ResponseEntity.status(403).body("Forbidden access to an apartment which is not yours");
            } */
           Apartment apartment = apartmentService.findById(id).orElseThrow();
           apartmentService.deleteById(id);

           return ResponseEntity.ok(apartmentService.toDTO(apartment));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
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

    // PENDIENTE -> Hacer un modelo que tenga más detalles para este controlador, o al revés para el resto
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

    // PENDIENTE -> Cambiar las URL de este método AJAX para que cuadren con el formato

    @GetMapping("/loadMore")
    public Page<ApartmentDTO> loadMoreApartments(
        Pageable pageable,
        @RequestParam (defaultValue = "0") int numPage,
        @RequestParam (defaultValue = "6") int pageSize) {

        List<Apartment> apartments = apartmentService.findAll();
        int totalCount = apartments.size();

        if (numPage * pageSize < totalCount) {
        /*     int actualEnd = Math.min(numPage * pageSize, totalCount);
            List<Apartment> paginatedApartments = apartments.subList(start, actualEnd);
            return ResponseEntity.ok(paginatedApartments); */

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
