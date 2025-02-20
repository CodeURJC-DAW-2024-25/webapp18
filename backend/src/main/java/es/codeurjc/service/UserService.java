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
        if (!sort.equals(null)) {
            return userRepository.findAll(sort);
        } else {
            return userRepository.findAll();
        }
    }

    @Override
    public Boolean exist(Long id) {
        Optional<UserE> user = userRepository.findById(id);
        if (user.isPresent())
            return true;
        else
            return false;
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

    public List<UserE> findByHotelInReservations(@Param("apartment") Apartment apartment){
        return userRepository.findByHotelInReservations(apartment);
    }

    

    public boolean existNick(String nick) {
        Optional<UserE> user = findByNick(nick);
        return user.isPresent();
    }

    public List<Apartment> findRecomendedHotels(int numHotels, List<Reservation> userReservations, UserE targetUser) {
        List<UserE> recomendedUsers = new ArrayList<>();
        List<Apartment> recomendedHotels = new ArrayList<>();

        for (Reservation reservation : userReservations) {
            Apartment reservedHotel = reservation.getApartment();
            recomendedUsers = userRepository.findByHotelInReservations(reservedHotel);
            if (recomendedUsers.contains(targetUser)) // removes self from recommendations
                recomendedUsers.remove(targetUser);
            for (UserE recommendedUser : recomendedUsers) {
                for (Reservation recommendedUserReservation : recommendedUser.getReservations()) {
                    Apartment recommendedHotel = recommendedUserReservation.getApartment();
                    Boolean validHotel = recommendedHotel.getManager().getvalidated();

                    if ((!recomendedHotels.contains(recommendedHotel)) && validHotel) {
                        recomendedHotels.add(recommendedHotel);
                        if (recomendedHotels.size() == (numHotels))
                            return recomendedHotels;
                    }
                }
            }
        }
        return recomendedHotels;
    }

}
