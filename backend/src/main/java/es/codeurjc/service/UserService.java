package es.codeurjc.service;

import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.dto.UserDTO;
import es.codeurjc.dto.UserMapper;
import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.UserE;
import es.codeurjc.repository.UserRepository;
import org.springframework.core.io.InputStreamResource;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;



@Service
public class UserService implements GeneralService<UserE> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserE> findById(Long id) {
        return userRepository.findById(id);

    }

    @Override
    public void save(UserE user) {
        userRepository.save(user);
    }

    @Override
    public void delete(UserE user) {
        userRepository.delete(user);
    }

    @Override
    public List<UserE> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserE> findAll(Sort sort) {
        if (sort != null) {
            return userRepository.findAll(sort);
        } else {
            return userRepository.findAll();
        }
    }

    @Override
    public Boolean exist(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public Optional<UserE> findByNick(String nick) {
        return userRepository.findByNick(nick);
    }

    public Optional<UserE> findFirstByName(String name) {
        return userRepository.findFirstByName(name);
    }

    public Optional<UserE> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<UserE> findByValidatedAndRejected(Boolean validated, Boolean rejected) {
        return userRepository.findByValidatedAndRejected(validated, rejected);
    }

    public List<UserE> findByRejected(Boolean validated) {
        return userRepository.findByRejected(validated);
    }

    public List<UserE> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<UserE> findLocationByName(String name) {
        return userRepository.findLocationByName(name);
    }

    public List<UserE> findByApartmentInReservations(@Param("apartment") Apartment apartment) {
        return userRepository.findByApartmentInReservations(apartment);
    }

    public boolean existNick(String nick) {
        Optional<UserE> user = findByNick(nick);
        return user.isPresent();
    }

    public List<Apartment> findRecommendedApartments(int numApartments, List<Reservation> userReservations,
            UserE targetUser) {
        List<Apartment> recommendedApartments = new ArrayList<>();
        List<UserE> recommendedUsers = new ArrayList<>();

        for (Reservation reservation : userReservations) {
            Apartment reservedApartment = reservation.getApartment();
            recommendedUsers = userRepository.findByApartmentInReservations(reservedApartment);
            if (recommendedUsers.contains(targetUser)) // removes self from recommendations
                recommendedUsers.remove(targetUser);
            for (UserE recommendedUser : recommendedUsers) {
                for (Reservation recommendedUserReservation : recommendedUser.getReservations()) {
                    Apartment recommendedApartment = recommendedUserReservation.getApartment();
                    Boolean validApartment = recommendedApartment.getManager().getValidated();

                    if ((!recommendedApartments.contains(recommendedApartment)) && validApartment) {
                        recommendedApartments.add(recommendedApartment);
                        if (recommendedApartments.size() == (numApartments))
                            return recommendedApartments;
                    }
                }
            }
        }
        return recommendedApartments;
    }

    public Resource getImage(Long id) throws SQLException {

        UserE user = userRepository.findById(id).orElseThrow();

        if (user.getImageFile() != null) {
            return new InputStreamResource(user.getImageFile().getBinaryStream());
        } else {
            throw new NoSuchElementException();
        }
    }

    public UserDTO replaceUser(Long id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {

            UserE user = mapper.toDomain(userDTO);
            user.setId(id);

            userRepository.save(user);

            return mapper.toDTO(user);

        } else {
            throw new NoSuchElementException();
        }

    }

    public void replaceUserImage(long id, InputStream inputStream, long size) {
        UserE user = userRepository.findById(id).orElseThrow();

        user.setImageFile(BlobProxy.generateProxy(inputStream, size));

        userRepository.save(user);
    }

    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {

        UserE newUser = mapper.toDomain(userDTO);
        newUser.setPass(passwordEncoder.encode(userDTO.pass()));
        save(newUser);
        
        URI location = fromCurrentRequest().path("/{id}")
        .buildAndExpand(userDTO.id()).toUri();

        return ResponseEntity.created(location).body(mapper.toDTO(newUser));
    }
}
