package es.codeurjc.dto;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ApartmentDTO (
     Long id,
     String name,
     String description,
     String location,
     float rating,
     @JsonIgnore
     Blob imageFile,
     String imagePath,
     boolean image,
     UserBasicDTO manager,
     List<RoomBasicDTO> rooms,
     List<ReservationBasicDTO> reservations,
     List<ReviewBasicDTO> reviews
){}