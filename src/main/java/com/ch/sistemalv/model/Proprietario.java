package com.ch.sistemalv.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Proprietario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "Primeiro nome é obrigatorio")
    private String primeiroNome;

    @Column(nullable = false)
    private String ultimoNome;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email é inválido, use o formato exemplo@email.com")
    private String email;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\([1-9]{2}\\) [9]{1}[0-9]{4}-[0-9]{4}$", message = "Telefone é inválido, use o formato (99) 99999-9999")
    private String telefone;
}
