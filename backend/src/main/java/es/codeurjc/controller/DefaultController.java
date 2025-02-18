package es.codeurjc.controller;

import org.springframework.ui.Model;


public class DefaultController {
    @GetMapping("/")
    public String showIndex(Model model){
        model.addAttribute("apartments", apartmentService.findAll());
    }
}
