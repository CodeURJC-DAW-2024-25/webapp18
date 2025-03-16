package es.codeurjc.service;

import es.codeurjc.dto.NewReservationDTO;
import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.dto.ReservationMapper;
import es.codeurjc.model.Reservation;
import es.codeurjc.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationDTO save(NewReservationDTO newReservationDTO) {
        Reservation reservation = ReservationMapper.toEntity(newReservationDTO);
        reservation = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(reservation);
    }

    public Optional<ReservationDTO> findById(Long id) {
        return reservationRepository.findById(id).map(ReservationMapper::toDTO);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
