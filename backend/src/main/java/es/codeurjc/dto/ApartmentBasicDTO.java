package es.codeurjc.dto;

import java.sql.Blob;

public record ApartmentBasicDTO(
     Long id,
     String name,
     String description,
     String location,
     float rating,
     Blob imageFile,
     String imagePath,
     boolean image,
     UserBasicDTO manager
     ) {}

//Pendiente cambiar userE or userEbasicDTO