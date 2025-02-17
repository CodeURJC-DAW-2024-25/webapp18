package es.codeurjc.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int numPeople;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private Room room;

    @ManyToOne
    private UserE user;

    public Reservation() {

    }

    public Reservation(LocalDate checkIn, LocalDate checkOut, int numPeople, Apartment apartment, Room room, UserE user) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numPeople = numPeople;
        this.apartment = apartment;
        this.room = room;
        this.user = user;
    }

    public Long getIdReservation() {
        return id;
    }

    public void setIdReservation(Long id) {
        this.id = id;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment Apartment) {
        this.apartment = Apartment;
    }

    public Room getRooms() {
        return room;
    }

    public void setRooms(Room room) {
        this.room = room;
    }

    public UserE getUser() {
        return user;
    }

    public void setUser(UserE user) {
        this.user = user;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

}
