package com.example.Examen1.controller;

import com.example.Examen1.models.Rueda;
import com.example.Examen1.services.RuedaService;
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
@RequestMapping("/ruedas")
public class RuedaController {

    @Autowired
    private RuedaService ruedaService;

    @GetMapping
    public ResponseEntity<List<Rueda>> getRuedas() {
        return ResponseEntity.ok(ruedaService.getRuedas());
    }

    @GetMapping("/{id}")
    ResponseEntity<Rueda> getRuedaById(@PathVariable Long id) {
        Rueda rueda = ruedaService.getRuedaById(id);
        if (rueda == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rueda);
    }

    @PostMapping
    ResponseEntity<?> postRueda(@Valid @RequestBody Rueda rueda) {
        Rueda saved = ruedaService.createRueda(rueda);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getRuedaId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> putRueda(@PathVariable Long id, @Valid @RequestBody Rueda rueda) {
        boolean updated = ruedaService.updateRueda(id, rueda);
        if (!updated) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchRueda(@PathVariable Long id, @RequestBody Rueda rueda) {
        return ruedaService.partiallyUpdateRueda(id, rueda)
                .map(updated -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRueda(@PathVariable Long id) {
        boolean result = ruedaService.deleteRueda(id);
        if (!result) return ResponseEntity.badRequest().build();
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handle(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

}
