package es.codeurjc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import es.codeurjc.model.Apartment;
import es.codeurjc.model.Reservation;
import es.codeurjc.model.Room;
import es.codeurjc.model.UserE;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.ReservationService;
import es.codeurjc.service.RoomService;
import es.codeurjc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/addReservation/{id}")
    public String showAddReservationForm(@PathVariable Long id, Model model) {
        // Either show a form or redirect to apartment information
        return "redirect:/apartmentInformation/" + id;
    }

    @PostMapping("/addReservation/{id}")
    public String addReservation(Model model, @PathVariable Long id, HttpServletRequest request, String checkIn,
            String checkOut, Integer numPeople) {

        LocalDate checkInDate = reservationService.toLocalDate(checkIn);
        LocalDate checkOutDate = reservationService.toLocalDate(checkOut);
        Room room = apartmentService.checkRooms(id, checkInDate, checkOutDate, numPeople);
        if (room != null) {
            UserE user = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
            Apartment apartment = apartmentService.findById(id).orElseThrow();
            Reservation newRe = new Reservation(checkInDate, checkOutDate, numPeople, apartment, room, user);
            reservationService.save(newRe);
            return "redirect:/clientReservations";
        } else
            // Fix: Use string concatenation instead of path variable syntax in redirect URL
            return "notRooms";
    }

    @GetMapping("/clientReservations")
    public String clientReservation(Model model, HttpServletRequest request) {
        UserE currentClient = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        List<Reservation> bookings = currentClient.getReservations();

        if (bookings.size() < 3) {
            model.addAttribute("reservations", bookings);

        } else {

            List<Reservation> auxBookings = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                auxBookings.add(bookings.get(i));
            }

            model.addAttribute("reservations", auxBookings);

        }

        model.addAttribute("user", currentClient);

        return "clientReservations";

    }

    @GetMapping("/loadMoreReservations/{start}/{end}")
    public String loadMoreReservations(
            Model model,
            HttpServletRequest request,
            @PathVariable int start,
            @PathVariable int end) {

        try {
            UserE currentClient = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
            List<Reservation> bookings = currentClient.getReservations();
            List<Reservation> auxBookings = new ArrayList<>();

            if (start < bookings.size()) {
                for (int i = start; i < end && i < bookings.size(); i++) {
                    auxBookings.add(bookings.get(i));
                }
            }

            model.addAttribute("reservations", auxBookings);
            return "reservationTemplate";

        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/reservationInfo/{id}")
    public String reservationInfo(Model model, HttpServletRequest request, @PathVariable Long id) {
        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE reservationUser = reservationService.findById(id).orElseThrow().getUser();

        if (currentUser.equals(reservationUser)) {
            model.addAttribute("reservation", reservationService.findById(id).orElseThrow());
            return "reservationInfo";
        } else
            return "/error";
    }

    @GetMapping("/cancelReservation/{id}")
    public String deleteReservation(HttpServletRequest request, @PathVariable Long id) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        UserE reservationUser = reservationService.findById(id).orElseThrow().getUser();

        if (currentUser.equals(reservationUser)) {
            Reservation reservation = reservationService.findById(id).orElseThrow();
            if (reservation != null) {
                reservation.getUser().getReservations().remove(reservation);
                reservation.getApartment().getReservations().remove(reservation);
                reservation.getRooms().getReservations().remove(reservation);

                userService.save(reservation.getUser());
                apartmentService.save(reservation.getApartment());
                roomService.save(reservation.getRooms());

                reservationService.deleteById(id);
            }
            return "redirect:/clientReservations";
        } else
            return "/error";
    }
}