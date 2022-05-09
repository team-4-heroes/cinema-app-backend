package kea.dat3.api;

import kea.dat3.dto.RoomRequest;
import kea.dat3.dto.RoomResponse;
import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.services.RoomService;
import kea.dat3.services.ScreeningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
public class RoomController {

    RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public RoomResponse addRoom(RoomRequest roomRequest) {
        return roomService.addRoom(roomRequest);
    }

    @GetMapping
    public List<RoomResponse> getRooms(){
        return roomService.getRooms();
    }
}
