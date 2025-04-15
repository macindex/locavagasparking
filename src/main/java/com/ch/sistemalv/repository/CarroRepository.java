package com.ch.sistemalv.repository;

import com.ch.sistemalv.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, String> {
    List<Carro> findByProprietarioId(String ownerId);
}
