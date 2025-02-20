package es.codeurjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.model.UserE;

@Repository
public interface UserRepository extends JpaRepository<UserE, Long> {

    // Buscar un usuario por su email
    boolean existsByEmail(String email);

    // Clase UserDTO interna
    class UserDTO {
        private String name;
        private String lastname;
        private String email;
        private String password;
        private String repeatPassword;

        // Constructor
        public UserDTO(String name, String lastname, String email, String password, String repeatPassword) {
            this.name = name;
            this.lastname = lastname;
            this.email = email;
            this.password = password;
            this.repeatPassword = repeatPassword;
        }

        // Getters y Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRepeatPassword() { return repeatPassword; }
        public void setRepeatPassword(String repeatPassword) { this.repeatPassword = repeatPassword; }
    }

    // Crear un usuario a partir de UserDTO
    default UserE createUser(UserDTO userDTO, String encodedPassword) {
        UserE user = new UserE();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPass(encodedPassword);  // Contraseña cifrada

        return save(user); // Guardar el usuario en la base de datos
    }
}
