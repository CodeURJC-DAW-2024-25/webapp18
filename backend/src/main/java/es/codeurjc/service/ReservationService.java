package es.codeurjc.service;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.codeurjc.model.Reservation;
import es.codeurjc.repository.ReservationRepository;

@Service
public class ReservationService implements GeneralService<Reservation> {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findByUser_Name(String name){
        return reservationRepository.findByUser_Name(name);
    }

    public List<Reservation> findByHotel_Name(String name){
        return reservationRepository.findByApartment_Name(name);
    } 

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAll(Sort sort) {
        if (sort == null) {
            return reservationRepository.findAll();
        } else {
            return reservationRepository.findAll(sort);
        }
    }
    
    @Override
    public Boolean exist(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.isPresent();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public LocalDate toLocalDate(String date) {
        if (date.contains("-")) {
            return LocalDate.parse(date); // This handles YYYY-MM-DD format directly
        } else {
            String[] list = date.split("/");
            return LocalDate.of(Integer.parseInt(list[2]), Integer.parseInt(list[0]),
                   Integer.parseInt(list[1]));
        }
    }

}

