package br.com.soupaulodev.avengersapi.application.web.resource;

import br.com.soupaulodev.avengersapi.application.web.resource.request.AvengerRequest;
import br.com.soupaulodev.avengersapi.application.web.resource.response.AvengerResponse;
import br.com.soupaulodev.avengersapi.domain.avenger.Avenger;
import br.com.soupaulodev.avengersapi.domain.avenger.AvengerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}/detail")
    public ResponseEntity<AvengerResponse> getAvengerDetails (@PathVariable("id") UUID id) {
        Optional<Avenger> avenger = this.repository.getDetail(id);

        if(avenger.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new AvengerResponse(avenger.get()));
    }

    @PostMapping
    public ResponseEntity<AvengerResponse> createAvenger (@Valid @RequestBody AvengerRequest request) {

        if(this.repository.getDetail(request.toAvenger().id()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Avenger avengerCreated = this.repository.create(request.toAvenger());

        return ResponseEntity
                .created(URI.create("/v1/api/avenger/" + avengerCreated.id()))
                .body(new AvengerResponse(avengerCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvengerResponse> updateAvenger (@Valid @RequestBody AvengerRequest request, @PathVariable("id") UUID id) {
        if (this.repository.getDetail(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Avenger avengerUpdated = this.repository.update(request.toAvenger(id));

        return ResponseEntity.accepted().body(new AvengerResponse(avengerUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvenger (@PathVariable("id") UUID id) {
        if (this.repository.getDetail(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        this.repository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
