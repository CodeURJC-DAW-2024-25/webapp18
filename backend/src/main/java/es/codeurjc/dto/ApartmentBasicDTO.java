package es.codeurjc.dto;

import java.sql.Blob;

import es.codeurjc.model.UserE;

public record ApartmentBasicDTO(
     Long id,
     String name,
     String description,
     String location,
     float rating,
     Blob imageFile,
     String imagePath,
     boolean image,
     UserE manager
     ) {}