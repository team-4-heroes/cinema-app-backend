package kea.dat3.api;

import kea.dat3.dto.RoomRequest;
import kea.dat3.dto.RoomResponse;
import kea.dat3.services.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public RoomResponse getRoom(@PathVariable long id) {
        return roomService.getRoom(id);
    }

    @GetMapping
    public List<RoomResponse> getRooms(){
        return roomService.getRooms();
    }

    @RolesAllowed("ADMIN")
    @PostMapping
    public RoomResponse addRoom(RoomRequest roomRequest) {
        return roomService.addRoom(roomRequest);
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("{id}")
    public void deleteRoom(@PathVariable long id) {
        roomService.delete(id);
    }

}
