package es.codeurjc.service;

import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.dto.ApartmentBasicDTO;
import es.codeurjc.dto.ApartmentDTO;
import es.codeurjc.dto.ApartmentMapper;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.Review;
import es.codeurjc.model.Room;
import es.codeurjc.model.UserE;
import es.codeurjc.repository.ApartmentRepository;

@Service
public class ApartmentService implements GeneralService<Apartment> {

    @Autowired
    ApartmentRepository apartmentRepository;

    @Autowired
    private ApartmentMapper mapper;

    // PENDIENTE -> Hacer los métodos save, create... que irían en el controlador

    public ApartmentDTO toDTO(Apartment apartment) {
        return mapper.toDTO(apartment);
    }

    public ApartmentBasicDTO toBasicDTO(Apartment apartment) {
        return mapper.toBasicDTO(apartment);
    }

    public List<ApartmentDTO> toDTOs(List<Apartment> apartments) {
        return mapper.toDTOs(apartments);
    }

    public List<ApartmentBasicDTO> toBasicDTOs(List<Apartment> apartments) {
        return mapper.toBasicDTOs(apartments);
    }

    public Apartment toDomain(ApartmentDTO apartmentDTO) {
        return mapper.toDomain(apartmentDTO);
    }

    public List<ApartmentDTO> getApartments() {
        return toDTOs(apartmentRepository.findAll());
    }

    public ApartmentDTO getApartment(Long id) {
        return toDTO(apartmentRepository.findById(id).orElseThrow());
    }

    @Override
    public void save(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    @Override
    public void delete(Apartment apartment) {
        apartmentRepository.delete(apartment);
    }

    @Override
    public Optional<Apartment> findById(Long id) {
        return apartmentRepository.findById(id);
    }

    public List<Apartment> findByName(String name) {
        return apartmentRepository.findByName(name);
    }

    public List<Apartment> findByLocation(String location) {
        return apartmentRepository.findByLocation(location);
    }

    public List<Apartment> findTop6ByManager_Validated(Boolean validated) {
        return apartmentRepository.findTop6ByManager_Validated(validated);
    }

    public List<Apartment> findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(Boolean validated,
            String searchValue) {
        return apartmentRepository.findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(validated,
                searchValue);
    }

    public List<ApartmentDTO> findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(
            String searchValue) {
        return mapper
                .toDTOs(apartmentRepository.findTop6ByManager_ValidatedAndNameContainingIgnoreCaseOrderByNameDesc(true,
                        searchValue));
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

    public Page<ApartmentDTO> findAll(Pageable pageable) {
        return apartmentRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    public Boolean exist(Long id) {
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        return apartment.isPresent();
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
        List<Room> rooms = apartment.getRooms();

        for (Room room : rooms) {
            if (room.getMaxClients() == numPeople && room.available(checkIn, checkOut)) {
                return room;
            }
        }

        // If exact match not found, try to find a room that can accommodate at least
        // that many
        for (Room room : rooms) {
            if (room.getMaxClients() >= numPeople && room.available(checkIn, checkOut)) {
                return room;
            }
        }

        return null;
    }

    public void deleteById(Long id) {
        apartmentRepository.deleteById(id);
    }

    public long count() {
        return apartmentRepository.count();
    }

    @Transactional
    public ApartmentDTO createApartment(ApartmentDTO newApartmentDTO) {
        Apartment newApartment = toDomain(newApartmentDTO);

        for (Room room : newApartment.getRooms()) {
            room.setApartment(newApartment);
        }
        for (Reservation reservation : newApartment.getReservations()) {
            reservation.setApartment(newApartment);
        }
        for (Review review : newApartment.getReviews()) {
            review.setApartment(newApartment);
        }

        apartmentRepository.save(newApartment);
        return toDTO(newApartment);
    }

    public ApartmentDTO updateApartment(ApartmentDTO updatedApartmentDTO, Long id) {

        Apartment updatedApartment = toDomain(updatedApartmentDTO);
        updatedApartment.setId(id);
        apartmentRepository.save(updatedApartment);

        return toDTO(updatedApartment);
    }

    public ApartmentDTO deleteApartment(Long id) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow();
        apartmentRepository.deleteById(id);
        return toDTO(apartment);
    }

    public Resource getImageFile(Long id) throws SQLException {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow();
        if (apartment.getImageFile() != null) {
            return new InputStreamResource(apartment.getImageFile().getBinaryStream());
        }
        throw new NoSuchElementException();
    }

    public void createImage(Long id, URI location, InputStream inputStream, long size) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow();

        apartment.setImagePath(location.toString());
        apartment.setImage(true);

        apartment.setImageFile(BlobProxy.generateProxy(inputStream, size));

        apartmentRepository.save(apartment);
    }

    public void editImage(Long id, InputStream inputStream, long size) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow();
        if (apartment.getImageFile() == null) {
            throw new NoSuchElementException();
        }
        apartment.setImageFile(BlobProxy.generateProxy(inputStream, size));
        apartmentRepository.save(apartment);
    }

    public void deleteImage(Long id) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow();

        if (apartment.getImageFile() == null) {
            throw new NoSuchElementException();
        }
        apartment.setImageFile(null);
        apartment.setImagePath(null);
        apartment.setImage(false);

        apartmentRepository.save(apartment);
    }

    public Page<ApartmentDTO> findByUser(UserE user, Pageable pageable) {
        return apartmentRepository.findByUser(user, pageable).map(this::toDTO);
    }

    public Map<Integer, Float> getPercentageOfScores(Apartment apartment) {

        Map<Integer, Float> percentageOfScores = new HashMap<>();
        List<Review> reviews = apartment.getReviews();
        int numReviews = reviews.size();

        for (Integer i=1; i<=5; i++){

            long reviewsWithiScore = reviews.stream().filter(review -> review.getScore() == i).count();
            float percentageOfiScore = ((float) reviewsWithiScore /  numReviews) * 100;
            
            percentageOfScores.put(i, percentageOfiScore);
            
        }

        return percentageOfScores;


    }

}
