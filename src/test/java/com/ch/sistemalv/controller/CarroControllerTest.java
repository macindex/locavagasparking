package com.ch.sistemalv.controller;

import com.ch.sistemalv.model.Carro;
import com.ch.sistemalv.model.Proprietario;
import com.ch.sistemalv.service.CarroServico;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarroController.class)
class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private CarroServico carroServico;

    @Autowired
    private ObjectMapper objectMapper;

    private Carro criarCarroFake() {
        Proprietario prop = Proprietario.builder()
                .id(String.valueOf(1L))
                .primeiroNome("João da Silva")
                .ultimoNome("12345678900")
                .build();

        return Carro.builder()
                .placa("ABC-1234")
                .modelo("Civic")
                .cor("Prata")
                .marca("Honda")
                .proprietario(prop)
                .build();
    }

    @Test
    void deveRetornarTodosOsCarros() throws Exception {
        when(carroServico.findAll()).thenReturn(List.of(criarCarroFake()));

        mockMvc.perform(get("/api/carros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].placa").value("ABC-1234"));
    }

    @Test
    void deveRetornarCarroPorId() throws Exception {
        Carro carro = criarCarroFake();
        when(carroServico.findById("1")).thenReturn(carro);

        mockMvc.perform(get("/api/carros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placa").value("ABC-1234"));
    }

    @Test
    void deveSalvarCarro() throws Exception {
        Carro carro = criarCarroFake();
        when(carroServico.save(any(Carro.class))).thenReturn(carro);

        mockMvc.perform(post("/api/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modelo").value("Civic"));
    }

    @Test
    void deveAtualizarCarro() throws Exception {
        Carro carro = criarCarroFake();
        when(carroServico.update(Mockito.eq("1"), any(Carro.class))).thenReturn(carro);

        mockMvc.perform(put("/api/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cor").value("Prata"));
    }

    @Test
    void deveDeletarCarro() throws Exception {
        Carro carro = criarCarroFake();
        when(carroServico.findById("1")).thenReturn(carro);
        Mockito.doNothing().when(carroServico).delete("1");

        mockMvc.perform(delete("/api/carros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404AoDeletarCarroInexistente() throws Exception {
        when(carroServico.findById("2")).thenReturn(null);

        mockMvc.perform(delete("/api/carros/2"))
                .andExpect(status().isNotFound());
    }
}

