package com.ch.sistemalv.service;

import com.ch.sistemalv.model.Carro;
import com.ch.sistemalv.model.Proprietario;
import com.ch.sistemalv.repository.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarroServicoTest {

    @InjectMocks
    private CarroServico carroServico;

    @Mock
    private CarroRepository carroRepository;

    private Carro carroExemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Proprietario proprietario = new Proprietario(899971234, "João", "Silva");
        proprietario.setId(String.valueOf(1L));
        proprietario.setPrimeiroNome("João");

        carroExemplo = new Carro();
        carroExemplo.setId(1L);
        carroExemplo.setPlaca("ABC-1234");
        carroExemplo.setModelo("Civic");
        carroExemplo.setCor("Prata");
        carroExemplo.setMarca("Honda");
        carroExemplo.setProprietario(proprietario);
    }

    @Test
    void deveBuscarTodosOsCarros() {
        when(carroRepository.findAll()).thenReturn(List.of(carroExemplo));

        List<Carro> carros = carroServico.findAll();

        assertThat(carros).hasSize(1).contains(carroExemplo);
        verify(carroRepository).findAll();
    }

    @Test
    void deveBuscarCarroPorId() {
        when(carroRepository.findById("1")).thenReturn(Optional.of(carroExemplo));

        Carro resultado = carroServico.findById("1");

        assertThat(resultado).isEqualTo(carroExemplo);
    }

    @Test
    void deveRetornarNullSeCarroNaoEncontradoPorId() {
        when(carroRepository.findById("1")).thenReturn(Optional.empty());

        Carro resultado = carroServico.findById("1");

        assertThat(resultado).isNull();
    }

    @Test
    void deveSalvarCarro() {
        when(carroRepository.save(carroExemplo)).thenReturn(carroExemplo);

        Carro salvo = carroServico.save(carroExemplo);

        assertThat(salvo).isEqualTo(carroExemplo);
        verify(carroRepository).save(carroExemplo);
    }

    @Test
    void deveAtualizarCarroExistente() {
        when(carroRepository.findById("1")).thenReturn(Optional.of(carroExemplo));
        when(carroRepository.save(any(Carro.class))).thenReturn(carroExemplo);

        Carro carroAtualizado = new Carro();
        carroAtualizado.setPlaca("XYZ-9999");
        carroAtualizado.setModelo("Fit");
        carroAtualizado.setCor("Preto");
    }
}