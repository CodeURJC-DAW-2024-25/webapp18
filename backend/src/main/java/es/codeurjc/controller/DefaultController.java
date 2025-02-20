package es.codeurjc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String showIndex() {
        return "index";  
    }

    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "contact";
    }

   
    
}
