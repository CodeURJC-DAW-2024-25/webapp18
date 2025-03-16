package es.codeurjc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.dto.NewRoomRequestDTO;
import es.codeurjc.dto.RoomDTO;
import es.codeurjc.service.ApartmentService;
import es.codeurjc.service.RoomService;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public String getAllRooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "rooms/list";
    }

    @GetMapping("/{id}")
    public String getRoomDetails(@PathVariable Long id, Model model) {
        roomService.findById(id).ifPresent(room -> model.addAttribute("room", room));
        return "rooms/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("apartments", apartmentService.findAll());
        return "rooms/form";
    }

    @PostMapping("/new")
    public String createRoom(@ModelAttribute NewRoomRequestDTO newRoomDTO) {
        roomService.save(newRoomDTO);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        roomService.findById(id).ifPresent(room -> model.addAttribute("room", room));
        model.addAttribute("apartments", apartmentService.findAll());
        return "rooms/form";
    }

    @PostMapping("/{id}/edit")
    public String updateRoom(@PathVariable Long id, @ModelAttribute RoomDTO roomDTO) {
        roomService.update(id, roomDTO);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        roomService.findById(id).ifPresent(room -> model.addAttribute("room", room));
        return "rooms/delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return "redirect:/rooms";
    }

    @GetMapping("/filter")
    public String filterByMaxClients(@RequestParam int maxClients, Model model) {
        model.addAttribute("rooms", roomService.findByMaxClients(maxClients));
        return "rooms/list";
    }
}
