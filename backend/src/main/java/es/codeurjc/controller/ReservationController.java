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

import es.codeurjc.dto.NewReservationDTO;
import es.codeurjc.dto.ReservationDTO;
import es.codeurjc.model.Apartment;
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
        return "redirect:/apartmentInformation/" + id;
    }

    @PostMapping("/addReservation/{id}")
    public String addReservation(Model model, @PathVariable Long id, HttpServletRequest request, String checkIn,
            String checkOut, Integer numPeople) {

        LocalDate checkInDate = reservationService.toLocalDate(checkIn);
        LocalDate checkOutDate = reservationService.toLocalDate(checkOut);
        Room room = apartmentService.checkRooms(id, checkInDate, checkOutDate, numPeople);
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            return "error";
        }
        
        if (room != null) {
            UserE user = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
            Apartment apartment = apartmentService.findById(id).orElseThrow();
            NewReservationDTO newReservationDTO = new NewReservationDTO();
            newReservationDTO.setCheckIn(checkInDate);
            newReservationDTO.setCheckOut(checkOutDate);
            newReservationDTO.setNumPeople(numPeople);
            newReservationDTO.setApartmentId(apartment.getId());
            newReservationDTO.setRoomId(room.getId());
            newReservationDTO.setUserId(user.getId());
            reservationService.save(newReservationDTO);
            return "redirect:/clientReservations";
        } else {
            return "notRooms";
        }
    }

    @GetMapping("/clientReservations")
    public String clientReservation(Model model, HttpServletRequest request) {
        UserE currentClient = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();

        List<ReservationDTO> bookings = new ArrayList<>();
        reservationService.findById(currentClient.getId()).ifPresent(bookings::add);

        if (bookings.size() < 3) {
            model.addAttribute("reservations", bookings);

        } else {

            List<ReservationDTO> auxBookings = new ArrayList<>();
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
            List<ReservationDTO> bookings = new ArrayList<>();
            reservationService.findById(currentClient.getId()).ifPresent(bookings::add);
            List<ReservationDTO> auxBookings = new ArrayList<>();

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
        ReservationDTO reservation = reservationService.findById(id).orElseThrow();

        if (currentUser.getId().equals(reservation.getUserId())) {
            model.addAttribute("reservation", reservation);
            return "reservationInfo";
        } else
            return "/error";
    }

    @GetMapping("/cancelReservation/{id}")
    public String deleteReservation(HttpServletRequest request, @PathVariable Long id) {

        UserE currentUser = userService.findByNick(request.getUserPrincipal().getName()).orElseThrow();
        ReservationDTO reservation = reservationService.findById(id).orElseThrow();

        if (currentUser.getId().equals(reservation.getUserId())) {
            reservationService.deleteById(id);
            return "redirect:/clientReservations";
        } else
            return "/error";
    }
}