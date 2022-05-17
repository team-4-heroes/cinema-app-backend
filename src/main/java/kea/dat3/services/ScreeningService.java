package kea.dat3.services;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Movie;
import kea.dat3.entities.Room;
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
        return screenings.stream().map(ScreeningResponse::new).collect(Collectors.toList());
    }

    public ScreeningResponse findById(long id) {
        return new ScreeningResponse(screeningRepository.findById(id).orElseThrow(() -> new ScreeningNotFoundException(id)));
    }

    public ScreeningResponse addScreening(ScreeningRequest screeningReq) {
        // check if chosen room and movie exists in DB
        Long movieId = screeningReq.getMovieId();
        Long roomId = screeningReq.getRoomId();
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException(movieId));
        Room room = roomRepository.findById(roomId).orElseThrow(()->new RoomNotFoundException(roomId));
        Screening screening;
        LocalDateTime start = screeningReq.getStartTime();
        LocalDateTime end = start.plusMinutes(movie.getLengthInMinutes());
        if (screeningRepository.isRoomAvailableForScreening(roomId, start, end)) {
            screening = screeningRepository.save(new Screening(start,room,movie));
        } else {
            throw new RoomOccupiedException(roomId);
        }
        return new ScreeningResponse(screening);
    }

    public ScreeningResponse updateScreening(long id, ScreeningRequest screeningRequest) {
        Screening screening = screeningRepository.findById(id).orElseThrow(() -> new ScreeningNotFoundException(id));
        if (screeningRequest.getRoomId() > 0) screening.setRoom(roomRepository.getById(screeningRequest.getRoomId()));
        if (screeningRequest.getMovieId() > 0) screening.setMovie(movieRepository.getById(screeningRequest.getMovieId()));
        if (screeningRequest.getStartTime() != null) screening.setStartTime(screeningRequest.getStartTime());
        return new ScreeningResponse(screeningRepository.save(screening));
    }

    public void deleteScreening(long id) {
        if (!screeningRepository.existsById(id)) throw new ScreeningNotFoundException(id);
        screeningRepository.deleteById(id);
    }
}
