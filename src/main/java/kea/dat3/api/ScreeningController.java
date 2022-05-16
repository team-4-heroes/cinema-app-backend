package kea.dat3.api;

import kea.dat3.dto.ScreeningRequest;
import kea.dat3.dto.ScreeningResponse;
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

    // @RolesAllowed("ADMIN") todo: uncomment and fix test
    @PostMapping
    public ScreeningResponse addScreening(@RequestBody ScreeningRequest screeningRequest) {
        return screeningService.addScreening(screeningRequest);
    }

    @GetMapping
    public List<ScreeningResponse> getScreenings(Pageable pageable){
        return screeningService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ScreeningResponse getScreening(@PathVariable long id) {
        return screeningService.findById(id);
    }
/* //TODO: Fix
    @PutMapping("/{id}")
    public ScreeningResponse putScreening(@PathVariable long id, @RequestBody ScreeningRequest screeningRequest) {
        return screeningService.updateScreening(id, screeningRequest);
    } */

    @DeleteMapping("/{id}")
    public void deleteScreening(@PathVariable long id) {
        screeningService.deleteScreening(id);
    }
}
