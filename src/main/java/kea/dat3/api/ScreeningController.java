package kea.dat3.api;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.entities.Screening;
import kea.dat3.services.ScreeningService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/screenings")
public class ScreeningController {

    ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping
    public ScreeningResponse addScreening(@RequestBody ScreeningRequest screeningRequest) {
        return screeningService.addScreening(screeningRequest);
    }

    @GetMapping
    public List<ScreeningResponse> getScreenings(Pageable pageable){
        return screeningService.getScreenings(pageable);
    }

    @GetMapping("/{id}")
    public ScreeningResponse getScreening(@PathVariable long id) {
        return screeningService.getScreening(id);
    }
}
