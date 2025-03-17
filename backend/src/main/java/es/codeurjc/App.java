package es.codeurjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(
pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("RUNNING APP. WELCOME TO DREAMLIFE :) :) :)");
        
        
        SpringApplication.run(App.class, args);
    }
}
