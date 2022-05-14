package kea.dat3.services;

import kea.dat3.dto.RoomRequest;
import kea.dat3.dto.RoomResponse;
import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Room;
import kea.dat3.entities.Screening;
import kea.dat3.repositories.RoomRepository;
import kea.dat3.repositories.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> getRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(room -> new RoomResponse(room, false)).collect(Collectors.toList());
    }

    public RoomResponse addRoom(RoomRequest roomRequest) {
        Room room = roomRepository.save(new Room(roomRequest));
        return new RoomResponse(room,true);
    }

}
