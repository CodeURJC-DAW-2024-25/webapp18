package es.codeurjc.controllerRest;

import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.dto.ReservationMapper;
import es.codeurjc.model.Reservation;
import es.codeurjc.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toDomain(reservationDTO);
        Reservation savedReservation = reservationService.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    @GetMapping("/{id}")
    public Optional<ReservationDTO> getReservation(@PathVariable Long id) {
        return reservationService.findById(id).map(reservationMapper::toDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}