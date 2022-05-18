package kea.dat3.entities.builders;

import kea.dat3.entities.Actor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ActorBuilder {

    private Actor actor;

    private ActorBuilder() {
    }

    public static ActorBuilder create(String firstName, String lastName, LocalDate birthDate) {
        var actorBuilder = new ActorBuilder();
        var actor = new Actor();
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        actor.setBirthDate(birthDate);
        actorBuilder.setActor(actor);
        return actorBuilder;
    }

    public static ActorBuilder create() {
        var actorBuilder = new ActorBuilder();
        var actor = new Actor();
        actorBuilder.setActor(actor);
        return actorBuilder;
    }

    private void setActor(Actor actor) {
        this.actor = actor;
    }

    public ActorBuilder addFirstName(String firstName) {
        actor.setFirstName(firstName);
        return this;
    }

    public ActorBuilder addLastName(String lastName) {
        actor.setLastName(lastName);
        return this;
    }

    public ActorBuilder addBirthDate(LocalDate birthDate) {
        actor.setBirthDate(birthDate);
        return this;
    }

    public ActorBuilder addCreated(LocalDateTime ldt) {
        actor.setCreated(ldt);
        return this;
    }

    public ActorBuilder addUpdated(LocalDateTime ldt) {
        actor.setUpdated(ldt);
        return this;
    }

    public ActorBuilder addId(Long id) {
        actor.setId(id);
        return this;
    }

    public Actor build() {
        var temp = actor;
        actor = null;
        return temp;
    }
}
