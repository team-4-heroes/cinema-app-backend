package kea.dat3.services;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Screening;
import kea.dat3.error.Client4xxException;
import kea.dat3.repositories.MovieRepository;
import kea.dat3.repositories.RoomRepository;
import kea.dat3.repositories.ScreeningRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    public ScreeningService(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public List<ScreeningResponse> findAll(Pageable pageable) {
        Page<Screening> screenings = screeningRepository.findAll(pageable);
        return screenings.stream().map(screening -> new ScreeningResponse(screening)).collect(Collectors.toList());
    }

    public ScreeningResponse findById(long id) {
        return new ScreeningResponse(screeningRepository.findById(id).orElseThrow(()->notFoundException()));
    }

    public ScreeningResponse addScreening(ScreeningRequest screeningReq) {
        // check if chosen room and movie exists in DB
        if (!roomRepository.existsById(screeningReq.getRoom().getId())) {
            throw new Client4xxException("Room nonexistent", HttpStatus.NOT_FOUND);
        }
        if (!movieRepository.existsById(screeningReq.getMovie().getId())) {
            throw new Client4xxException("Movie nonexistent", HttpStatus.NOT_FOUND);
        }
        Screening screening;
        long roomId = screeningReq.getRoom().getId();
        LocalDateTime start = screeningReq.getStartTime();
        LocalDateTime end = start.plusMinutes(screeningReq.getMovie().getLengthInMinutes());
        if (screeningRepository.isRoomAvailableForScreening(roomId, start, end)) {
            screening = screeningRepository.save(new Screening(screeningReq));
        } else {
            throw new Client4xxException("Room occupied", HttpStatus.CONFLICT);
        }
        return new ScreeningResponse(screening);
    }
    // TODO: test this
    public ScreeningResponse updateScreening(long id, ScreeningRequest screeningRequest) {
        if (!screeningRepository.existsById(id)) throw notFoundException();
        return new ScreeningResponse(screeningRepository.save(new Screening(screeningRequest)));
    }
    // does it return 404 if screening not found
    public void deleteScreening(long id) {
        if (!screeningRepository.existsById(id)) throw notFoundException();
        screeningRepository.deleteById(id);
    }
    public Client4xxException notFoundException() {
        return new Client4xxException("No screening with this id", HttpStatus.NOT_FOUND);
    }
}
