package br.com.soupaulodev.avengersapi.domain.avenger;

import java.util.UUID;

public record Avenger(
    UUID id,
    String nick,
    String person,
    String description,
    String history
) {

    public Avenger(String nick, String person, String description, String history) {
        this(null, nick, person, description, history);
    }
}
