package com.ch.sistemalv.controller;

import com.ch.sistemalv.model.Carro;
import com.ch.sistemalv.service.CarroServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carros")
public class CarroController {

    private final CarroServico carroServico;

    public CarroController(CarroServico carroServico) {
        this.carroServico = carroServico;
    }

    @GetMapping
    public ResponseEntity<List<Carro>> findAll() {
        List<Carro> carros = carroServico.findAll();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> findById(@PathVariable String id) {
        return ResponseEntity.of(Optional.ofNullable(carroServico.findById(id)));
    }

    @GetMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<List<Carro>> findByProprietario(@PathVariable String proprietarioId) {
        List<Carro> carros = carroServico.findByProprietario(proprietarioId);
        return ResponseEntity.ok(carros);
    }
    @PostMapping
    public ResponseEntity<Carro> save(@Valid @RequestBody Carro carro) {
        Carro novoCarro = carroServico.save(carro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCarro);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Carro> update(@PathVariable String id, @Valid @RequestBody Carro carro) {
        Carro atualizado = carroServico.update(id, carro);
        return (atualizado != null)
                ? ResponseEntity.ok(atualizado)
                : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Carro carro = carroServico.findById(id);

        if (carro != null) {
            carroServico.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}

