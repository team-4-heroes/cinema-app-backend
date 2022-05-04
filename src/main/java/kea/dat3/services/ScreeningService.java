package kea.dat3.services;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Screening;
import kea.dat3.repositories.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<ScreeningResponse> getScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        return screenings.stream().map(screening -> new ScreeningResponse(screening)).collect(Collectors.toList());
    }

    public ScreeningResponse addScreening(ScreeningRequest screeningReq) {
        Screening screening = screeningRepository.save(new Screening(screeningReq));
        return new ScreeningResponse(screening);
    }

}
