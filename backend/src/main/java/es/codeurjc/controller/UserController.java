package es.codeurjc.controller;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  // Anotación @Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; // Anotación @GetMapping
import org.springframework.web.bind.annotation.PostMapping; // Anotación @PostMapping

import es.codeurjc.model.UserE;
import es.codeurjc.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Mostrar la página de inicio de sesión
    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre del archivo login.html dentro de templates
    }

    // Mostrar la página de perfil
    @GetMapping("/profile")
    public String showProfile() {
        return "profile"; // Nombre del archivo profile.html en /templates
    }

    // Mostrar la página de registro
    @GetMapping("/register")
    public String showRegister() {
        return "register"; // Nombre del archivo register.html en /templates
    }

    // Procesar el formulario de registro
    @PostMapping("/register")
    public String register(String name, String lastname, String email, String password, String repeatPassword, Model model) {
        try {
            // Registrar al usuario usando UserService
            UserE user = userService.registerUser(name, lastname, email, password, repeatPassword);

            // Si el registro es exitoso, redirigir a la página de login
            model.addAttribute("message", "Usuario registrado con éxito");
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            // Si hay un error, mostrar el mensaje en la misma página de registro
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
