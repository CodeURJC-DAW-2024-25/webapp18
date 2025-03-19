package es.codeurjc.dto;

import java.sql.Blob;
import java.util.List;


import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.Review;
import jakarta.persistence.Lob;

public record UserDTO( 

    Long id,

    String name,

    String lastname,

    String bio,

    String location,

    String language,

    String phone,

    String email,

    Blob imageFile,

    boolean image,

    String organization,

    Boolean validated,

    Boolean rejected,

    List<String> rols,

    String nick,

    String pass,

    List<ApartmentBasicDTO> apartments,

    List<ReservationBasicDTO> reservations,

    List<ReviewBasicDTO> reviews
) {}
