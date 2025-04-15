package com.ch.sistemalv.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CarroTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarCarroValido() {
        Proprietario proprietario = Proprietario.builder()
                .id(String.valueOf(1L))
                .primeiroNome("João da Silva")
                .ultimoNome("Silva")
                .build();

        Carro carro = Carro.builder()
                .placa("ABC-1234")
                .modelo("Civic")
                .cor("Prata")
                .marca("Honda")
                .proprietario(proprietario)
                .build();

        Set<ConstraintViolation<Carro>> violations = validator.validate(carro);
        assertThat(violations).isEmpty();
    }

    @Test
    void deveDetectarPlacaInvalida() {
        Carro carro = new Carro();
        carro.setPlaca("123-ABCD"); // formato inválido
        carro.setModelo("Gol");
        carro.setCor("Preto");
        carro.setMarca("VW");
        carro.setProprietario(new Proprietario(1L, "Maria", "11122233344"));

        Set<ConstraintViolation<Carro>> violations = validator.validate(carro);
        assertThat(violations).extracting("propertyPath").extracting(Object::toString).contains("placa");
    }

    @Test
    void deveDetectarCamposObrigatoriosVazios() {
        Carro carro = new Carro(); // objeto vazio

        Set<ConstraintViolation<Carro>> violations = validator.validate(carro);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(4); // placa, modelo, cor, marca e proprietário
    }
}

