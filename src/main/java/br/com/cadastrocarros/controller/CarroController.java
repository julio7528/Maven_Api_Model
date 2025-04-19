package br.com.cadastrocarros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastrocarros.model.Carro;
import br.com.cadastrocarros.repository.CarroRepository;

@RestController
@RequestMapping("/api/carros")

public class CarroController {
    private final CarroRepository carroRepository;

    // Constructor
    @Autowired
    public CarroController(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    // Post
    @PostMapping
    public ResponseEntity<Carro> criarCarro(@RequestBody Carro carro) {
        try {
            Carro novoCarro = carroRepository.save(carro);
            return new ResponseEntity<>(novoCarro, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get list all cars
    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros() {
        try {
            List<Carro> carros = carroRepository.findAll();
            if (carros.isEmpty()) {
                return new ResponseEntity<>(carros, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(carros, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Carro> obterCarroPorId(@PathVariable Long id) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        return carroOptional
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get by marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Carro>> obterCarrosPorMarca(@PathVariable String marca) {
        List<Carro> carros = carroRepository.findByMarca(marca);
        if (carros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    // Put
    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id, @RequestBody Carro carro) {
        Optional<Carro> carroExistenteOptional = carroRepository.findById(id);

        if (carroExistenteOptional.isPresent()) {
            Carro carroExistente = carroExistenteOptional.get();
            carroExistente.setMarca(carro.getMarca());
            carroExistente.setModelo(carro.getModelo());
            carroExistente.setAno(carro.getAno());

            Carro carroSalvo = carroRepository.save(carroExistente);
            return new ResponseEntity<>(carroSalvo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCarro(@PathVariable Long id) {
        try {
            if (carroRepository.existsById(id)) {
                carroRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

