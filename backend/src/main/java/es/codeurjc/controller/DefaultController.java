package es.codeurjc.controller;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.service.ApartmentService;

@Controller
public class DefaultController {

    @Autowired
    private ApartmentService apartmentService;

    // Página de inicio - Muestra los apartamentos
    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("apartments", apartmentService.findAll());
        return "index"; // Sin barra "/" porque Spring busca en /templates/index.html
    }
}
