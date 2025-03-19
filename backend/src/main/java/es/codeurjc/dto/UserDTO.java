package es.codeurjc.dto;

import java.sql.Blob;
import java.util.List;

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
