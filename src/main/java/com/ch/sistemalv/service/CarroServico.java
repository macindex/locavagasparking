package com.ch.sistemalv.service;

import com.ch.sistemalv.model.Carro;
import com.ch.sistemalv.repository.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarroServico {
    private final CarroRepository carroRepository;

    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    public Carro findById(String id) {
        return carroRepository.findById(id).orElse(null);
    }

    public List<Carro> findByProprietario(String proprietarioId) {
        return carroRepository.findByProprietarioId(proprietarioId);
    }

    public Carro save(Carro car) {
        return carroRepository.save(car);
    }

    public Carro update(String id, Carro car) {
        Carro carToUpdate = carroRepository.findById(id).orElse(null);

        if (carToUpdate != null) {
            carToUpdate.setPlaca(car.getPlaca());
            carToUpdate.setModelo(car.getModelo());
            carToUpdate.setCor(car.getCor());
            carToUpdate.setMarca(car.getMarca());

            return carroRepository.save(carToUpdate);
        }

        return null;
    }

    public void delete(String id) {
        carroRepository.deleteById(id);
    }
}
