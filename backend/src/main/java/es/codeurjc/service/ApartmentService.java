package es.codeurjc.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.Room;
import es.codeurjc.model.UserE;
import es.codeurjc.repository.ApartmentRepository;

@Service
public class ApartmentService implements GeneralService<Apartment> {

    @Autowired
    ApartmentRepository apartmentRepository;

    @Override
    public void save(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    @Override
    public void delete(Apartment apartment) {
        apartmentRepository.delete(apartment);
        ;
    }
    
    @Override
    public Optional<Apartment> findById(Long id) {
        return apartmentRepository.findById(id);
    }

    public List<Apartment> findByName(String name){
        return apartmentRepository.findByName(name);
    }

    public List<Apartment> findByLocation(String location){
        return apartmentRepository.findByLocation(location);
    }

    public List<Apartment> findTop6ByManager_Validated(Boolean validated){
        return apartmentRepository.findTop6ByManager_Validated(validated);
    }

    public List<Apartment> findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(Boolean validated,
            String searchValue){
                return apartmentRepository.findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(validated, searchValue);
            }

    @Override
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    @Override
    public List<Apartment> findAll(Sort sort) {
        if (!sort.equals(null)) {
            return apartmentRepository.findAll(sort);
        } else {
            return apartmentRepository.findAll();
        }
    }

    @Override
    public Boolean exist(Long id) {
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        if (apartment.isPresent())
            return true;
        else
            return false;
    }

    public List<UserE> getValidClients(Apartment apartment) {
        LocalDate today = LocalDate.now();
        List<UserE> validClients = new ArrayList<>();
        List<Reservation> apartmentReservations = apartment.getReservations();

        for (Reservation reservation : apartmentReservations) {
            if (reservation.getCheckOut().isAfter(today)) {
                UserE user = reservation.getUser();
                if (!validClients.contains(user))
                    validClients.add(user);
            }
        }
        return validClients;
    }

    public Room checkRooms(Long id, LocalDate checkIn, LocalDate checkOut, Integer numPeople) {
        Apartment apartment = this.findById(id).orElseThrow();
        Integer i = 0;
        List<Room> rooms = apartment.getRooms();
        Boolean roomLocated = false;
        Room room = null;
        while ((i < apartment.getNumRooms()) && !(roomLocated)) {
            if (rooms.get(i).getMaxClients() == numPeople) {
                if (rooms.get(i).available(checkIn, checkOut)) {
                    room = rooms.get(i);
                    roomLocated = true;
                } else
                    i++;
            } else
                i++;
        }
        return room;
    }

    public void deleteById(Long id){
        apartmentRepository.deleteById(id);
    }

    public long count(){
        return apartmentRepository.count();
    }

}
