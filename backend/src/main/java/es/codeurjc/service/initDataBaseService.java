package es.codeurjc.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;

import java.util.Random;

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
    @Transactional
    private void initDatabase() throws IOException {

        List<String> rolesUser = new ArrayList<>();
        rolesUser.add("USER");
        rolesUser.add("CLIENT");
        List<String> rolesManager = new ArrayList<>();
        rolesManager.add("USER");
        rolesManager.add("MANAGER");
        List<String> rolesAdmin = new ArrayList<>();
        rolesAdmin.add("USER");
        rolesAdmin.add("ADMIN");

        // updates database if its already loaded
/*         if (!userRepository.findByNick("admin").isPresent()) { */

            UserE client1 = new UserE("Jack1", "Wells1", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "user", passwordEncoder.encode("pass"), null, null, rolesUser,
                    new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>());

            UserE client2 = new UserE("Jack4", "Wells4", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "user2", passwordEncoder.encode("pass2"), null, null, rolesUser,
                    new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>());

            UserE client3 = new UserE("Jack6", "Wells6", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "user3", passwordEncoder.encode("pass3"), null, null, rolesUser,
                    new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>());

            UserE manager1 = new UserE("Jack2", "Wells2", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "manager", passwordEncoder.encode("manager"), true, false,
                    rolesManager,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            UserE manager2 = new UserE("Jack2", "Wells2", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "manager2", passwordEncoder.encode("manager2"), true, false,
                    rolesManager,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            UserE manager3 = new UserE("Jack5", "Wells5", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "manager3", passwordEncoder.encode("manager3"), false, false,
                    rolesManager,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            UserE admin = new UserE("Jack3", "Wells3", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "admin", passwordEncoder.encode("admin"), null, null, rolesAdmin,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            UserE manager5 = new UserE("Jack5", "Wells5", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "manager5", passwordEncoder.encode("manager5"), true, false,
                    rolesManager,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            UserE manager6 = new UserE("Jack6", "Wells6", "Bio", "loc", "lan", "phone",
                    "mail", "org", null, "manager6", passwordEncoder.encode("manager6"), true, false,
                    rolesManager,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            userRepository.save(manager1);
            userRepository.save(manager2);
            userRepository.save(manager3);
            userRepository.save(manager5);
            userRepository.save(manager6);
            userRepository.save(client1);
            userRepository.save(client2);
            userRepository.save(client3);
            userRepository.save(admin);

            // init rooms
            Room room1 = new Room(2, 200F, new ArrayList<>(), null);
            Room room2 = new Room(2, 200F, new ArrayList<>(), null);
            Room room3 = new Room(3, 300F, new ArrayList<>(), null);

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            // init Apartments
            Apartment apartment1 = new Apartment("Apartment1", "Apartamento de plata", "loc1", 0F, null, manager1,
                    new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>());

            Apartment apartment2 = new Apartment("Apartment2", "Apartamento ", "loc2", 0F, null, manager2,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment3 = new Apartment("Apartment3", "Apartamento ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment4 = new Apartment("Apartment4", "Apartamento ", "loc2", 0F, null, manager5,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment5 = new Apartment("Apartment5", "Apartamento ", "loc2", 0F, null, manager5,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment6 = new Apartment("Apartment6", "Apartamento ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment7 = new Apartment("Apartment7", "Apartamento ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment8 = new Apartment("Apartment8", "apartment ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment9 = new Apartment("Apartment9", "apartment ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment10 = new Apartment("Apartment10", "apartment ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            Apartment apartment11 = new Apartment("Apartment11", "apartment ", "loc2", 0F, null, manager3,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>());

            apartmentRepository.save(apartment1);
            apartmentRepository.save(apartment2);
            apartmentRepository.save(apartment3);
            apartmentRepository.save(apartment4);
            apartmentRepository.save(apartment5);
            apartmentRepository.save(apartment6);
            apartmentRepository.save(apartment7);
            apartmentRepository.save(apartment8);
            apartmentRepository.save(apartment9);
            apartmentRepository.save(apartment10);
            apartmentRepository.save(apartment11);

            // add apartment to rooms
            room1.setApartment(apartment1);
            room2.setApartment(apartment2);
            room3.setApartment(apartment3);

            // save rooms
            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            // add apartment to manager
            manager1.getApartment().add(apartment1);
            manager2.getApartment().add(apartment2);
            manager2.getApartment().add(apartment3);

            userRepository.save(manager1);
            userRepository.save(manager2);

            // init reservation
            Reservation reservation1 = new Reservation(LocalDate.of(2024, 2, 27), LocalDate.of(2024, 2, 28), 2,
                    apartment1, room1, client1);
            Reservation reservation2 = new Reservation(LocalDate.of(2024, 3, 4), LocalDate.of(2024, 3, 6), 2,
                    apartment2, room2, client3);
            Reservation reservation3 = new Reservation(LocalDate.of(2024, 6, 4), LocalDate.of(2024, 6, 6), 2,
                    apartment2, room2, client2);
            Reservation reservation4 = new Reservation(LocalDate.of(2012, 6, 4), LocalDate.of(2013, 6, 6), 2,
                    apartment3, room3, client3);
            Reservation reservation5 = new Reservation(LocalDate.of(2012, 6, 5), LocalDate.of(2014, 6, 6), 2,
                    apartment3, room3, client3);
            Reservation reservation6 = new Reservation(LocalDate.of(2012, 6, 6), LocalDate.of(2015, 6, 6), 2,
                    apartment3, room3, client3);
            Reservation reservation7 = new Reservation(LocalDate.of(2012, 6, 7), LocalDate.of(2016, 6, 6), 2,
                    apartment3, room3, client3);
            Reservation reservation8 = new Reservation(LocalDate.of(2012, 6, 8), LocalDate.of(2017, 6, 6), 2,
                    apartment3, room3, client3);
            Reservation reservation9 = new Reservation(LocalDate.of(2012, 6, 9), LocalDate.of(2018, 6, 6), 2,
                    apartment3, room3, client3);

            reservationRepository.save(reservation1);
            reservationRepository.save(reservation2);
            reservationRepository.save(reservation3);

            reservationRepository.save(reservation4);
            reservationRepository.save(reservation5);
            reservationRepository.save(reservation6);
            reservationRepository.save(reservation7);
            reservationRepository.save(reservation8);
            reservationRepository.save(reservation9);

            // add reservation to apartment
            apartment1.getReservations().add(reservation1);
            apartment2.getReservations().add(reservation2);
            apartment2.getReservations().add(reservation3);
            apartment3.getReservations().add(reservation4);

            apartment2.getReservations().add(reservation5);
            apartment2.getReservations().add(reservation6);
            apartment2.getReservations().add(reservation7);
            apartment2.getReservations().add(reservation8);
            apartment2.getReservations().add(reservation9);

            apartmentRepository.save(apartment1);
            apartmentRepository.save(apartment2);
            apartmentRepository.save(apartment3);

            // add reservation to room
            room1.getReservations().add(reservation1);
            room2.getReservations().add(reservation2);
            room2.getReservations().add(reservation3);
            room3.getReservations().add(reservation4);

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            // add reservation to client
            client1.getReservations().add(reservation1);
            client1.getReservations().add(reservation2);
            client2.getReservations().add(reservation3);
            client3.getReservations().add(reservation4);

            userRepository.save(client1);
            userRepository.save(client2);
            userRepository.save(client3);

            // init review
            Review review1 = new Review(4, "Hola", LocalDate.of(2024, 3, 2), apartment1, client1);
            Review review2 = new Review(4, "Hola", LocalDate.of(2024, 7, 7), apartment2, client2);

            reviewRepository.save(review1);
            reviewRepository.save(review2);

            // add review to apartment
            apartment1.getReviews().add(review1);
            apartment1.getReviews().add(review2);

            Random randomInt = new Random();

            for (int i = 0; i < 10; i++) {
                Review review = new Review(randomInt.nextInt(5) + 1, "Hola" + i, LocalDate.of(2024, 7, 7),
                        apartment3, client1);
                reviewRepository.save(review);
                apartment2.getReviews().add(review);
            }

            apartmentRepository.save(apartment1);
            apartmentRepository.save(apartment2);

            // add review to client
            client1.getReviews().add(review1);
            client2.getReviews().add(review2);

            userRepository.save(client1);
            userRepository.save(client2);

            // images for all type of users that we created
            setImage(client1, "/static/images/userPhoto.jpg");
            setImage(client2, "/static/images/client2.jpg");
            setImage(client3, "/static/images/client3.jpg");
            setImage(manager1, "/static/images/manager.jpg");
            setImage(manager1, "/static/images/manager.jpg");
            setImage(manager2, "/static/images/manager2.jpg");
            setImage(manager3, "/static/images/manager3.jpg");
            setImage(manager3, "/static/images/manager3.jpg");
            setImage(manager3, "/static/images/manager2.jpg");
            setImage(admin, "/static/images/admin.jpg");
            userRepository.save(client1);
            userRepository.save(client2);
            userRepository.save(client3);
            userRepository.save(manager1);
            userRepository.save(manager2);
            userRepository.save(manager3);
            userRepository.save(manager5);
            userRepository.save(manager6);
            userRepository.save(admin);

            // images for hotels
            setImage(apartment1, "/static/images/apartment7.jpg");
            setImage(apartment2, "/static/images/apartment1.jpg");
            setImage(apartment3, "/static/images/apartment2.jpg");
            setImage(apartment4, "/static/images/apartment4.jpg");
            setImage(apartment5, "/static/images/apartment3.jpg");
            setImage(apartment6, "/static/images/apartment4.jpg");
            setImage(apartment7, "/static/images/apartment6.jpg");
            setImage(apartment8, "/static/images/apartment5.jpg");
            setImage(apartment9, "/static/images/apartment6.jpg");
            setImage(apartment10, "/static/images/apartment4.jpg");
            setImage(apartment11, "/static/images/apartment7.jpg");
            apartmentRepository.save(apartment1);
            apartmentRepository.save(apartment2);
            apartmentRepository.save(apartment3);
            apartmentRepository.save(apartment4);
            apartmentRepository.save(apartment5);
            apartmentRepository.save(apartment6);
            apartmentRepository.save(apartment7);
            apartmentRepository.save(apartment8);
            apartmentRepository.save(apartment9);
            apartmentRepository.save(apartment10);
            apartmentRepository.save(apartment11);
            
            // Crear lista con los apartamentos del 1 al 6
            List<Apartment> targetApartments = new ArrayList<>();
            targetApartments.add(apartment1);
            targetApartments.add(apartment2);
            targetApartments.add(apartment3);
            targetApartments.add(apartment4);
            targetApartments.add(apartment5);
            targetApartments.add(apartment6);
            
            Random random = new Random();
            
            // Crear y asignar 20 habitaciones adicionales a los apartamentos 1-6
            for (int i = 1; i <= 20; i++) {
                int capacity = random.nextInt(4) + 1;
                float cost = capacity * 100F;
                
                // Seleccionar un apartamento de la lista específica
                Apartment assignedApartment = targetApartments.get(random.nextInt(targetApartments.size()));
                
                // Crear y asociar la habitación
                Room newRoom = new Room(capacity, cost, new ArrayList<>(), assignedApartment);
                roomRepository.save(newRoom);
                assignedApartment.getRooms().add(newRoom);
                apartmentRepository.save(assignedApartment);
            }
  /*       } */
    }
    
    // for user images
    public void setImage(UserE user, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        user.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        user.setImage(true);
    }

    // for apartments images
    public void setImage(Apartment apartment, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        apartment.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        apartment.setImage(true);
    }
}