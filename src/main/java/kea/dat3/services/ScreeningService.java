package kea.dat3.services;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Screening;
import kea.dat3.error.MovieNotFoundException;
import kea.dat3.error.RoomNotFoundException;
import kea.dat3.error.RoomOccupiedException;
import kea.dat3.error.ScreeningNotFoundException;
import kea.dat3.repositories.MovieRepository;
import kea.dat3.repositories.RoomRepository;
import kea.dat3.repositories.ScreeningRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

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
        return new ScreeningResponse(screeningRepository.findById(id).orElseThrow(() -> new ScreeningNotFoundException(id)));
    }

    public ScreeningResponse addScreening(ScreeningRequest screeningReq) {
        // check if chosen room and movie exists in DB
        Long movieId = screeningReq.getMovie().getId();
        Long roomId = screeningReq.getRoom().getId();
        if (roomId == null || !roomRepository.existsById(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        if (movieId == null || !movieRepository.existsById(movieId)) {
            throw new MovieNotFoundException(movieId);
        }
        Screening screening;
        LocalDateTime start = screeningReq.getStartTime();
        LocalDateTime end = start.plusMinutes(screeningReq.getMovie().getLengthInMinutes());
        if (screeningRepository.isRoomAvailableForScreening(roomId, start, end)) {
            screening = screeningRepository.save(new Screening(screeningReq));
        } else {
            throw new RoomOccupiedException(roomId);
        }
        return new ScreeningResponse(screening);
    }

    // TODO: test this
    public ScreeningResponse updateScreening(long id, ScreeningRequest screeningRequest) {
        if (!screeningRepository.existsById(id)) throw new ScreeningNotFoundException(id);
        return new ScreeningResponse(screeningRepository.save(new Screening(screeningRequest)));
    }

    // does it return 404 if screening not found
    public void deleteScreening(long id) {
        if (!screeningRepository.existsById(id)) throw new ScreeningNotFoundException(id);
        screeningRepository.deleteById(id);
    }
}
