package es.codeurjc.controller;

// Spring
import org.springframework.stereotype.Controller;  // Importar la anotación @Controller
import org.springframework.web.bind.annotation.GetMapping; // Importar la anotación @GetMapping

@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre del archivo login.html dentro de templates
    }
    @GetMapping("/profile")
    public String showProfile() {
        return "profile"; // Nombre del archivo profile.html en /templates
    }
}
