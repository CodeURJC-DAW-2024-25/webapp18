package es.codeurjc.controllerRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.codeurjc.model.UserE;
import es.codeurjc.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserE> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserE> getUserById(@PathVariable Long id) {
        Optional<UserE> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserE> createUser(@RequestBody UserE user) {
        if (userService.existNick(user.getNick())) {
            return ResponseEntity.badRequest().build();
        }
        UserE savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserE> updateUser(@PathVariable Long id, @RequestBody UserE userDetails) {
        Optional<UserE> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            UserE user = userOptional.get();
            user.setName(userDetails.getName());
            user.setLocation(userDetails.getLocation());
            user.setOrganization(userDetails.getOrganization());
            user.setLanguage(userDetails.getLanguage());
            user.setPhone(userDetails.getPhone());
            user.setEmail(userDetails.getEmail());
            user.setBio(userDetails.getBio());
            userService.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
