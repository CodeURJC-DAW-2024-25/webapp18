package es.codeurjc.dto;
import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserBasicDTO( 
    Long id,
    @JsonIgnore
    Blob imageFile,
    boolean image,
    String imagePath,
    List<String> rols,
    String nick

) {}
