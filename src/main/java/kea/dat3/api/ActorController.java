package kea.dat3.api;

import kea.dat3.dto.ActorRequest;
import kea.dat3.dto.ActorResponse;
import kea.dat3.services.ActorService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/actors")
public class ActorController {

    ActorService actorService;

    @GetMapping()
    Set<ActorResponse> getActors() {
        return actorService.getActors();
    }

    @GetMapping("/by-name")
    Set<ActorResponse> getActorsByName(@RequestParam String firstName, @RequestParam String lastName) {
        return actorService.getActorsByName(firstName, lastName);
    }

    @GetMapping("/{id}")
    ActorResponse getActor(@PathVariable Long id) {
        return actorService.getActor(id);
    }

    @PostMapping
    ActorResponse addActor(@RequestBody ActorRequest body) {
        return actorService.addActor(body);
    }
}
