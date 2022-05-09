package kea.dat3.services;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Screening;
import kea.dat3.error.Client4xxException;
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

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<ScreeningResponse> getScreenings(Pageable pageable) {
        Page<Screening> screenings = screeningRepository.findAll(pageable);
        return screenings.stream().map(screening -> new ScreeningResponse(screening)).collect(Collectors.toList());
    }

    public ScreeningResponse getScreening(long id) {
        return new ScreeningResponse(screeningRepository.findById(id).orElseThrow(
                ()->new Client4xxException("Unavailable screening", HttpStatus.NOT_FOUND)));
    }

    public ScreeningResponse addScreening(ScreeningRequest screeningReq) {
        // TODO: check if chosen room and movie exists in DB
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

}
