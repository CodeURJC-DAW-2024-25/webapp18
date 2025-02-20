package es.codeurjc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.codeurjc.service.RoomService;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

}
