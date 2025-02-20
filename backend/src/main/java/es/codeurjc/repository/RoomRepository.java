package es.codeurjc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.backend.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByMaxClients(int maxClients); 
}
