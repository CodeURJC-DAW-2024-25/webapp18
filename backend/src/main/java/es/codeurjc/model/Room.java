package es.codeurjc.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int maxClients;

    private float cost;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    @ManyToOne
    private Apartment apartment;

    public Room() {

    }

    public Room(int maxClients, float cost, List<Reservation> reservations, Apartment apartment) {
        this.maxClients = maxClients;
        this.cost = cost;
        this.reservations = reservations;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public float getcost() {
        return cost;
    }

    public void setcost(float cost) {
        this.cost = cost;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public boolean available(LocalDate checkIn, LocalDate checkOut) {
        Boolean available = true;
        int i = 0;
        // Check if the room is available for the dates
        while (i < this.reservations.size() && available) {
            if (checkIn.isAfter(this.reservations.get(i).getCheckIn())
                    && checkIn.isBefore(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkOut.isAfter(this.reservations.get(i).getCheckIn())
                    && checkOut.isBefore(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isBefore(this.reservations.get(i).getCheckIn())
                    && checkOut.isAfter(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isEqual(this.reservations.get(i).getCheckIn())
                    || checkOut.isEqual(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isEqual(this.reservations.get(i).getCheckIn())
                    && checkOut.isEqual(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isAfter(this.reservations.get(i).getCheckIn())
                    && checkOut.isBefore(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isEqual(this.reservations.get(i).getCheckIn())
                    && checkOut.isAfter(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isBefore(this.reservations.get(i).getCheckIn())
                    && checkOut.isEqual(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isAfter(this.reservations.get(i).getCheckIn())
                    && checkOut.isEqual(this.reservations.get(i).getCheckOut()))
                available = false;
            else if (checkIn.isEqual(this.reservations.get(i).getCheckIn())
                    && checkOut.isBefore(this.reservations.get(i).getCheckOut()))
                available = false;
            else
                i++;
        }
        return available;
    }
}

