package kea.dat3.services;

import kea.dat3.dto.RoomRequest;
import kea.dat3.dto.RoomResponse;
import kea.dat3.entities.Room;
import kea.dat3.error.RoomNotFoundException;
import kea.dat3.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

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

    public RoomResponse getRoom(long id) {
        Room room = roomRepository.findById(id).orElseThrow(()->new RoomNotFoundException(id));
        return new RoomResponse(room,true);
    }

    public void delete(long id) {
        roomRepository.findById(id).orElseThrow(()->new RoomNotFoundException(id));
        roomRepository.deleteById(id);
    }
}
