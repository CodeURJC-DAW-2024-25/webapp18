package es.codeurjc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.model.UserE;
import es.codeurjc.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserE registerUser(String name, String lastname, String email, String password, String repeatPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        String encodedPassword = passwordEncoder.encode(password);
        UserRepository.UserDTO userDTO = new UserRepository.UserDTO(name, lastname, email, password, repeatPassword);

        return userRepository.createUser(userDTO, encodedPassword);
    }
}





