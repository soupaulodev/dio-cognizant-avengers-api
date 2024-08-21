package br.com.soupaulodev.avengersapi.domain.avenger;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvengerRepository {
    Optional<Avenger> getDetail(UUID id);
    List<Avenger>getAvengers();
    Avenger create(Avenger avenger);
    Avenger update(Avenger avenger);
    void delete(UUID id);
}