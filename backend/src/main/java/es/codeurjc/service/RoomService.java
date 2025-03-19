package es.codeurjc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import es.codeurjc.dto.RoomDTO;
import es.codeurjc.dto.RoomMapper;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Room;
import es.codeurjc.repository.ApartmentRepository;
import es.codeurjc.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private ApartmentRepository apartmentRepository;
    
    @Autowired
    private RoomMapper roomMapper;

    public Optional<RoomDTO> findById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toDTO);
    }

    public List<RoomDTO> findByMaxClients(int maxClients) {
        return roomMapper.toDTOs(roomRepository.findByMaxClients(maxClients));
    }

    public RoomDTO save(RoomDTO newRoomDTO) {
        Room room = new Room();
        room.setMaxClients(newRoomDTO.maxClients());
        room.setcost(newRoomDTO.cost());
        
        // find the apt and assign
        Apartment apartment = apartmentRepository.findById(newRoomDTO.apartment().id()) //REVISAR----------------------
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        room.setApartment(apartment);
        
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDTO(savedRoom);
    }
    
    public RoomDTO save(Room room) {
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDTO(savedRoom);
    }
    
    
    public Optional<RoomDTO> update(Long id, RoomDTO roomDTO) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setMaxClients(roomDTO.maxClients());
                    room.setcost(roomDTO.cost());
                    // just save room
                    return roomRepository.save(room);
                })
                .map(roomMapper::toDTO);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    public List<RoomDTO> findAll() {
        return roomMapper.toDTOs(roomRepository.findAll());
    }

    public List<RoomDTO> findAll(Sort sort) {
        if (sort == null) {
            return roomMapper.toDTOs(roomRepository.findAll());
        } else {
            return roomMapper.toDTOs(roomRepository.findAll(sort));
        }
    }
    
    public Boolean exist(Long id) {
        return roomRepository.findById(id).isPresent();
    }
}