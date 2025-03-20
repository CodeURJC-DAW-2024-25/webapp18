package es.codeurjc.controllerRest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.model.UserE;
import es.codeurjc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.dto.UserDTO;
import es.codeurjc.dto.UserMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserE> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserE> getUserById(@PathVariable Long id) {
        Optional<UserE> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<UserE> createUser(@RequestBody UserE user) {
        if (userService.existNick(user.getNick())) {
            return ResponseEntity.badRequest().build();
        }
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    /*
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
     * if (userService.findById(id).isPresent()) {
     * userService.deleteById(id);// pendiente deberiamos de añadir si hay tiempo en
     * el service un deleteById
     * return ResponseEntity.noContent().build();
     * } else {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

    // permite a un manager poder ser validado de nuevo
    @PutMapping("/{id}/application")
    public ResponseEntity<?> managerApplication(HttpServletRequest request, @PathVariable Long id) {
        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE foundManager = userService.findById(id).orElseThrow();

        if (currentUser.equals(foundManager)) {
            foundManager.setRejected(false);
            userService.save(foundManager);
            return ResponseEntity.ok(foundManager);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not the user that you think you are");
        }
    }

    @GetMapping("/")
    public ResponseEntity<Collection<UserDTO>> managerValidation(@RequestParam(required = false) Boolean validated,
            @RequestParam(required = false) Boolean rejected) {
        Collection<UserE> unvalidatedManagersList = userService.findByValidatedAndRejected(validated, rejected);

        if (unvalidatedManagersList != null && !unvalidatedManagersList.isEmpty()) {
            Collection<UserDTO> unvalidatedManagersDTOList = mapper.toDTOs(unvalidatedManagersList);
            return ResponseEntity.ok(unvalidatedManagersDTOList);
        } else {
            return ResponseEntity.noContent().build();
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO,
            HttpServletRequest request,
            @RequestParam(required = false) Boolean rejected, @RequestParam(required = false) Boolean validated) {
        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        // Only an admin can change the validated and rejected fields, so if a user tries to do it, it will return a 403 status code
        if (!(rejected == null && validated == null) && (!currentUser.getRols().contains("ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        UserE user = mapper.toDomain(userDTO);
        user.setId(id);
        userService.save(user);

        return ResponseEntity.ok(mapper.toDTO(user));

    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable Long id) throws SQLException, IOException {

        Resource image = userService.getImage(id);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(image);

    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> replacePostImage(HttpServletRequest request, @PathVariable long id,
            @RequestParam MultipartFile imageFile)
            throws IOException {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE foundUser = userService.findById(id).orElseThrow();

        if (currentUser.equals(foundUser)) {
            userService.replacePostImage(id, imageFile.getInputStream(), imageFile.getSize());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are trying to change the image of another user");
        }

    }


    //PENDIENTE -> cambiar el nombre a esta ruta, y revisar el controlador
    @PostMapping("/editProfileImage/{id}")
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
            return "redirect:/editProfile/" + id;

        } else
            return "/error";

    }

    // pendiente las imagenes van aparte en otro fichero
    // pendientes de hacer register seria un post, login post, prfile que es un get

}
