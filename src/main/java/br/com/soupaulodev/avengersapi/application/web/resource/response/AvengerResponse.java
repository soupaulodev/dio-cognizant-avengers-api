package br.com.soupaulodev.avengersapi.application.web.resource.response;

import br.com.soupaulodev.avengersapi.domain.avenger.Avenger;

import java.util.UUID;

public record AvengerResponse(
    UUID id,
    String nick,
    String person,
    String description,
    String history
) {
    public AvengerResponse (Avenger avenger) {
        this(avenger.id(), avenger.nick(), avenger.person(), avenger.description(), avenger.history() );
    }
}