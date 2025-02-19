package es.codeurjc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class ApartmentService {

    
    public List<String> findAll() {
        List<String> apartments = new ArrayList<>();
        apartments.add("Apartamento bladibla");

        return apartments;
    }
}
