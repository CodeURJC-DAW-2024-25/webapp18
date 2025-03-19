package es.codeurjc.dto;

import java.sql.Blob;
import java.util.List;
import es.codeurjc.model.UserE;

public record ApartmentDTO (
     Long id,
     String name,
     String description,
     String location,
     float rating,
     Blob imageFile,
     String imagePath,
     boolean image,
     UserE manager,
     List<RoomBasicDTO> rooms,
     List<ReservationDTO> reservations,
     List<ReviewDTO> reviews
){}