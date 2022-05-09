package kea.dat3.services;

import kea.dat3.dto.ActorRequest;
import kea.dat3.dto.ActorResponse;
import kea.dat3.entities.Actor;
import kea.dat3.repositories.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class ActorService {

    private ActorRepository actorRepository;

    public Set<ActorResponse> getActors() {
        return ActorResponse.getActorsFromEntities(actorRepository.findAll());
    }

    public ActorResponse getActor(Long id) {
        return new ActorResponse(actorRepository.getById(id));
    }

    public Set<ActorResponse> getActorsByName(String firstName, String lastName) {
        return ActorResponse.getActorsFromEntities(actorRepository.findActorByFirstNameAndLastName(firstName, lastName));
    }

    public ActorResponse addActor(ActorRequest body) {
        var actor = new Actor(body);
        return new ActorResponse(actorRepository.save(actor));
    }
}
