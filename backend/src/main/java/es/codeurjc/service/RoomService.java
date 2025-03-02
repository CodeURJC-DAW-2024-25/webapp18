package es.codeurjc.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.codeurjc.model.Room;
import es.codeurjc.repository.RoomRepository;

@Service
public class RoomService implements GeneralService<Room> {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);

    }

    public List<Room> findByMaxClients(int maxClients){
        return roomRepository.findByMaxClients(maxClients);
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAll(Sort sort) {
        if (sort == null) {
            return roomRepository.findAll();
        } else {
            return roomRepository.findAll(sort);
        }
    }
    
    @Override
    public Boolean exist(Long id) {
        return roomRepository.findById(id).isPresent();
    }
}
