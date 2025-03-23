package es.codeurjc.service;

import es.codeurjc.dto.ReservationBasicDTO;
import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.dto.ReservationMapper;
import es.codeurjc.model.Reservation;
import es.codeurjc.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationMapper mapper;

    ReservationDTO toDTO(Reservation reservation) {
        return mapper.toDTO(reservation);
    }

    Reservation toDomain(ReservationDTO dto) {
        return mapper.toDomain(dto);
    }

    ReservationBasicDTO toBasicDTO(Reservation reservation) {
        return mapper.toBasicDTO(reservation);
    }

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(checkIn, formatter);

    }
}