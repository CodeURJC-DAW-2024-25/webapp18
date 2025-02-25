package es.codeurjc.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.Review;
import es.codeurjc.model.Room;
import es.codeurjc.model.UserE;
import es.codeurjc.repository.ApartmentRepository;
import es.codeurjc.repository.ReservationRepository;
import es.codeurjc.repository.ReviewRepository;
import es.codeurjc.repository.RoomRepository;
import es.codeurjc.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class initDataBaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDatabase() throws IOException {

        if (userRepository.count() > 0) {
            return; // Evita duplicados si la base de datos ya tiene registros
        }

        List<String> rolesUser = List.of("ROLE_USER", "ROLE_CLIENT");
        List<String> rolesManager = List.of("ROLE_USER", "ROLE_MANAGER");
        List<String> rolesAdmin = List.of("ROLE_USER", "ROLE_ADMIN");

        // Crear usuarios con los roles correctos
        UserE client1 = createUser("Jack1", "Wells1", "user1@mail.com", "user", "pass", rolesUser);
        UserE client2 = createUser("Jack2", "Wells2", "user2@mail.com", "user2", "pass2", rolesUser);
        UserE client3 = createUser("Jack3", "Wells3", "user3@mail.com", "user3", "pass3", rolesUser);
        UserE manager1 = createUser("Manager1", "WellsM1", "manager1@mail.com", "manager", "manager", rolesManager);
        UserE manager2 = createUser("Manager2", "WellsM2", "manager2@mail.com", "manager2", "manager2", rolesManager);
        UserE manager3 = createUser("Manager3", "WellsM3", "manager3@mail.com", "manager3", "manager3", rolesManager);
        UserE admin = createUser("Admin", "Adminson", "admin@mail.com", "admin", "admin", rolesAdmin);

        // Guardar usuarios
        userRepository.saveAll(List.of(client1, client2, client3, manager1, manager2, manager3, admin));

        // Crear y guardar habitaciones
        Room room1 = new Room(2, 200F, new ArrayList<>(), null);
        Room room2 = new Room(2, 200F, new ArrayList<>(), null);
        Room room3 = new Room(3, 300F, new ArrayList<>(), null);

        roomRepository.saveAll(List.of(room1, room2, room3));

        // Crear y guardar apartamentos
        Apartment apartment1 = createApartment("Apartment1", manager1);
        Apartment apartment2 = createApartment("Apartment2", manager2);
        Apartment apartment3 = createApartment("Apartment3", manager3);

        apartmentRepository.saveAll(List.of(apartment1, apartment2, apartment3));

        // Vincular habitaciones a apartamentos
        room1.setApartment(apartment1);
        room2.setApartment(apartment2);
        room3.setApartment(apartment3);

        roomRepository.saveAll(List.of(room1, room2, room3));

        // Crear y guardar reservas
        reservationRepository.saveAll(List.of(
            createReservation(client1, apartment1, room1, LocalDate.of(2024, 2, 27), LocalDate.of(2024, 2, 28)),
            createReservation(client1, apartment2, room2, LocalDate.of(2024, 3, 4), LocalDate.of(2024, 3, 6)),
            createReservation(client2, apartment2, room2, LocalDate.of(2024, 6, 4), LocalDate.of(2024, 6, 6)),
            createReservation(client3, apartment1, room1, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 5)),
            createReservation(client3, apartment2, room2, LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 15)),
            createReservation(client3, apartment3, room3, LocalDate.of(2024, 9, 20), LocalDate.of(2024, 9, 25))
        ));

        // Crear y guardar reseñas
        reviewRepository.saveAll(List.of(
            createReview(client1, apartment1, 4, "Great place!"),
            createReview(client2, apartment2, 5, "Excellent stay!")
        ));

        // Asignar imágenes
        setImage(client1, "/static/images/userPhoto.jpg");
        setImage(manager1, "/static/images/manager.jpg");
        setImage(admin, "/static/images/admin.jpg");
        setImage(apartment1, "/static/images/hotel.jpg");
        setImage(apartment2, "/static/images/hotel1.jpg");

        userRepository.saveAll(List.of(client1, manager1, admin));
        apartmentRepository.saveAll(List.of(apartment1, apartment2));
    }

    private UserE createUser(String name, String lastname, String email, String nick, String pass, List<String> roles) {
        return new UserE(name, lastname, "Bio", "loc", "lan", "phone", email, "org", null, nick, passwordEncoder.encode(pass), null, null, roles, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private Apartment createApartment(String name, UserE manager) {
        return new Apartment(name, "Nice place", "loc", 0F, null, manager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private Reservation createReservation(UserE client, Apartment apartment, Room room, LocalDate start, LocalDate end) {
        return new Reservation(start, end, 2, apartment, room, client);
    }

    private Review createReview(UserE client, Apartment apartment, int rating, String comment) {
        return new Review(rating, comment, LocalDate.now(), apartment, client);
    }

    private void setImage(UserE user, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        user.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        user.setImage(true);
    }

    private void setImage(Apartment apartment, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        apartment.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        apartment.setImage(true);
    }
}
