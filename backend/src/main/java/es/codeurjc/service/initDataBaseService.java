package es.codeurjc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Room;
import es.codeurjc.model.UserE;
import es.codeurjc.repository.ApartmentRepository;
import es.codeurjc.repository.ReservationRepository;
import es.codeurjc.repository.ReviewRepository;
import es.codeurjc.repository.RoomRepository;
import es.codeurjc.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

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
        List<String> rolesUser = List.of("USER", "CLIENT");
        List<String> rolesManager = List.of("USER", "MANAGER");
        List<String> rolesAdmin = List.of("USER", "ADMIN");

        UserE client1 = new UserE("Jack1", "Wells1", "Bio", "loc", "lan", "phone", "mail", "org", null, "user", passwordEncoder.encode("pass"), null, null, rolesUser, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE client2 = new UserE("Jack4", "Wells4", "Bio", "loc", "lan", "phone", "mail", "org", null, "user2", passwordEncoder.encode("pass2"), null, null, rolesUser, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE client3 = new UserE("Jack6", "Wells6", "Bio", "loc", "lan", "phone", "mail", "org", null, "user3", passwordEncoder.encode("pass3"), null, null, rolesUser, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE manager1 = new UserE("Jack2", "Wells2", "Bio", "loc", "lan", "phone", "mail", "org", null, "manager", passwordEncoder.encode("manager"), true, false, rolesManager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE manager2 = new UserE("Jack2", "Wells2", "Bio", "loc", "lan", "phone", "mail", "org", null, "manager2", passwordEncoder.encode("manager2"), true, false, rolesManager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE manager3 = new UserE("Jack5", "Wells5", "Bio", "loc", "lan", "phone", "mail", "org", null, "manager3", passwordEncoder.encode("manager3"), false, false, rolesManager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE admin = new UserE("Jack3", "Wells3", "Bio", "loc", "lan", "phone", "mail", "org", null, "admin", passwordEncoder.encode("admin"), null, null, rolesAdmin, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE manager5 = new UserE("Jack5", "Wells5", "Bio", "loc", "lan", "phone", "mail", "org", null, "manager5", passwordEncoder.encode("manager5"), true, false, rolesManager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UserE manager6 = new UserE("Jack6", "Wells6", "Bio", "loc", "lan", "phone", "mail", "org", null, "manager6", passwordEncoder.encode("manager6"), true, false, rolesManager, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        List<UserE> allUsers = List.of(client1, client2, client3, manager1, manager2, manager3, manager5, manager6, admin);
        userRepository.saveAll(allUsers);

        Room room1 = new Room(2, 200F, new ArrayList<>(), null);
        Room room2 = new Room(2, 200F, new ArrayList<>(), null);
        Room room3 = new Room(3, 300F, new ArrayList<>(), null);
        roomRepository.saveAll(List.of(room1, room2, room3));

        Apartment apartment1 = new Apartment("Apartment1", "Apartamento de plata", "loc1", 0F, null, manager1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment2 = new Apartment("Apartment2", "Apartamento ", "loc2", 0F, null, manager2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment3 = new Apartment("Apartment3", "Apartamento ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment4 = new Apartment("Apartment4", "Apartamento ", "loc2", 0F, null, manager5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment5 = new Apartment("Apartment5", "Apartamento ", "loc2", 0F, null, manager5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment6 = new Apartment("Apartment6", "Apartamento ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment7 = new Apartment("Apartment7", "Apartamento ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment8 = new Apartment("Apartment8", "apartment ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment9 = new Apartment("Apartment9", "apartment ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment10 = new Apartment("Apartment10", "apartment ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Apartment apartment11 = new Apartment("Apartment11", "apartment ", "loc2", 0F, null, manager3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        List<Apartment> allApartments = List.of(
            apartment1, apartment2, apartment3, apartment4, apartment5, apartment6,
            apartment7, apartment8, apartment9, apartment10, apartment11
        );
        apartmentRepository.saveAll(allApartments);

        setImage(client1, "/static/images/userPhoto.jpg");
        setImage(client2, "/static/images/client2.jpg");
        setImage(client3, "/static/images/client3.jpg");
        setImage(manager1, "/static/images/manager.jpg");
        setImage(manager2, "/static/images/manager.jpg");
        setImage(manager3, "/static/images/manager2.jpg");
        setImage(manager6, "/static/images/manager2.jpg");
        setImage(admin, "/static/images/admin.jpg");
        userRepository.saveAll(List.of(client1, client2, client3, manager1, manager2, manager3, manager6, admin));

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
        apartmentRepository.saveAll(allApartments);
    }

    public void setImage(UserE user, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        user.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        user.setImage(true);
    }

    public void setImage(Apartment apartment, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        apartment.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        apartment.setImage(true);
    }
}
