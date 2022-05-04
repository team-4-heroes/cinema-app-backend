package kea.dat3.api;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
import kea.dat3.services.ScreeningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/screenings")
public class ScreeningController {

    ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping
    public ScreeningResponse addScreening(ScreeningRequest screeningRequest) {
        return screeningService.addScreening(screeningRequest);
    }

    @GetMapping
    public List<ScreeningResponse> getScreenings(){
        return screeningService.getScreenings();
    }
}
