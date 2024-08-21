package br.com.soupaulodev.avengersapi.application.web.resource;

import br.com.soupaulodev.avengersapi.application.web.resource.request.AvengerRequest;
import br.com.soupaulodev.avengersapi.application.web.resource.response.AvengerResponse;
import br.com.soupaulodev.avengersapi.domain.avenger.Avenger;
import br.com.soupaulodev.avengersapi.domain.avenger.AvengerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/avenger")
public class AvengerResource {

    private final AvengerRepository repository;

    public AvengerResource (AvengerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<AvengerResponse>> getAvengers () {
        List<AvengerResponse> response = this.repository.getAvengers().stream().map(AvengerResponse::new).toList();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvengerResponse> getAvengerDetails (@PathVariable("id") UUID id) {
        AvengerResponse response = new AvengerResponse(this.repository
                .getDetail(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)));

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<AvengerResponse> createAvenger (@Valid @RequestBody AvengerRequest request) {
        Avenger avengerCreated = this.repository.create(request.toAvenger());

        AvengerResponse response = new AvengerResponse(avengerCreated);
        URI uri = URI.create("/v1/api/avenger/" + avengerCreated.id());

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvengerResponse> updateAvenger (@Valid @RequestBody AvengerRequest request, @PathVariable("id") UUID id) {
        Avenger avengerDB = this.repository
                .getDetail(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        if (avengerDB == null) {
            return ResponseEntity.notFound().build();
        }

        AvengerResponse response = new AvengerResponse(this.repository.update(request.toAvenger()));

        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvenger (@PathVariable("id") UUID id) {
        this.repository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
