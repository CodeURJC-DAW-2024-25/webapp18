package es.codeurjc.controllerRest;

import es.codeurjc.dto.NewReservationDTO;
import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ReservationDTO createReservation(@RequestBody NewReservationDTO newReservationDTO) {
        return reservationService.save(newReservationDTO);
    }

    @GetMapping("/{id}")
    public Optional<ReservationDTO> getReservation(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}