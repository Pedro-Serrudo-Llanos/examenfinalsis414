package com.example.Examen1.controller;

import com.example.Examen1.models.Bicicleta;
import com.example.Examen1.services.BicicletaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bicicletas")

@CrossOrigin(origins = "https://peppy-kheer-4ac26c.netlify.app")
public class BicicletaController {

    @Autowired
    private BicicletaService bicicletaService;

    @GetMapping
    public ResponseEntity<List<Bicicleta>> getBicicletas() {
        return ResponseEntity.ok(bicicletaService.getBicicletas());
    }

    @GetMapping("/{id}")
    ResponseEntity<Bicicleta> getBicicletaById(@PathVariable Long id) {
        Bicicleta bicicleta = bicicletaService.getBicicletaById(id);
        if (bicicleta == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bicicleta);
    }

    @PostMapping
    ResponseEntity<?> postBicicleta(@Valid @RequestBody Bicicleta bicicleta) {
        Bicicleta saved = bicicletaService.createBicicleta(bicicleta);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> putBicicleta(@PathVariable Long id, @Valid @RequestBody Bicicleta bicicleta) {
        boolean updated = bicicletaService.updateBicicleta(id, bicicleta);
        if (!updated) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchBicicleta(@PathVariable Long id, @RequestBody Bicicleta bicicleta) {
        return bicicletaService.partiallyUpdateBicicleta(id, bicicleta)
                .map(updated -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBicicleta(@PathVariable Long id) {
        boolean result = bicicletaService.deleteBicicleta(id);
        if (!result) return ResponseEntity.badRequest().build();
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handle(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }


}
