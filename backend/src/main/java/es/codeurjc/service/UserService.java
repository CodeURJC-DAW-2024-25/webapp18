package es.codeurjc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.UserE;
import es.codeurjc.repository.UserRepository;

@Service
public class UserService implements GeneralService<UserE> {

    @Autowired
    private UserRepository userRepository;


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

    public List<UserE> findByValidatedAndRejected(Boolean validated, Boolean rejected){
        return userRepository.findByValidatedAndRejected(validated, rejected);
    }

    public List<UserE> findByRejected(Boolean validated){
        return userRepository.findByRejected(validated);
    }
    
    public List<UserE> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<UserE> findLocationByName(String name) {
        return userRepository.findLocationByName(name);
    }

    public List<UserE> findByApartmentInReservations(@Param("apartment") Apartment apartment){
        return userRepository.findByApartmentInReservations(apartment);
    }

    

    public boolean existNick(String nick) {
        Optional<UserE> user = findByNick(nick);
        return user.isPresent();
    }

    public List<Apartment> findRecomendedApartments(int numApartments, List<Reservation> userReservations, UserE targetUser) {
        List<Apartment> recomendedApartments = new ArrayList<>();
        List<UserE> recomendedUsers = new ArrayList<>();

        for (Reservation reservation : userReservations) {
            Apartment reservedApartment = reservation.getApartment();
            recomendedUsers = userRepository.findByApartmentInReservations(reservedApartment);
            if (recomendedUsers.contains(targetUser)) // removes self from recommendations
                recomendedUsers.remove(targetUser);
            for (UserE recommendedUser : recomendedUsers) {
                for (Reservation recommendedUserReservation : recommendedUser.getReservations()) {
                    Apartment recommendedApartment = recommendedUserReservation.getApartment();
                    Boolean validApartment = recommendedApartment.getManager().getvalidated();

                    if ((!recomendedApartments.contains(recommendedApartment)) && validApartment) {
                        recomendedApartments.add(recommendedApartment);
                        if (recomendedApartments.size() == (numApartments))
                            return recomendedApartments;
                    }
                }
            }
        }
        return recomendedApartments;
    }

}
