package es.codeurjc.dto;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ApartmentBasicDTO(
     Long id,
     String name,
     String description,
     String location,
     float rating,
     @JsonIgnore
     Blob imageFile,
     String imagePath,
     boolean image
     ) {}