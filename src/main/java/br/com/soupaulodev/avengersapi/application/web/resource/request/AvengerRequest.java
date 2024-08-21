package br.com.soupaulodev.avengersapi.application.web.resource.request;

import br.com.soupaulodev.avengersapi.domain.avenger.Avenger;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AvengerRequest(
    @NotNull
    @NotBlank
    @NotEmpty
    String nick,
    @NotNull
    @NotBlank
    @NotEmpty
    String person,
    String description,
    String history
) {
    public AvengerRequest(String nick, String person) {
        this(nick, person, "", "");
    }

    public Avenger toAvenger() {
        return new Avenger(null, nick, person, description, history);
    }
}
