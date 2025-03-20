package es.codeurjc.service;

import es.codeurjc.dto.ReservationMapper;
import es.codeurjc.model.Reservation;
import es.codeurjc.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationMapper mapper;

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation save(Reservation newReservation) {
        return reservationRepository.save(newReservation);
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public LocalDate toLocalDate(String checkIn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toLocalDate'");
    }
}