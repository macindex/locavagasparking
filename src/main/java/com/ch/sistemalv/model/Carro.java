package com.ch.sistemalv.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "carros", uniqueConstraints = @UniqueConstraint(columnNames = "placa"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Uma placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}-[0-9]{4}$", message = "Placa inválida, use o formato AAA-0000")
    @Column(nullable = false, unique = true, length = 8)
    private String placa;

    @NotBlank(message = "Um modelo é obrigatório")
    @Column(nullable = false, length = 50)
    private String modelo;

    @NotBlank(message = "Uma cor é obrigatória")
    @Column(nullable = false, length = 30)
    private String cor;

    @NotBlank(message = "Uma marca é obrigatória")
    @Column(nullable = false, length = 30)
    private String marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id", nullable = false)
    @NotNull(message = "Um proprietário é obrigatório")
    private Proprietario proprietario;
}
