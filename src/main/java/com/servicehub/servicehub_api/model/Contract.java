package com.servicehub.servicehub_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do contrato", example = "1")
    private Long id;

    @NotNull  ( message = "O cliente é obrigatorio")
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    @Schema(description = "Provedor associado ao contrato")
    private Provider provider;

    @NotNull (message = " O cliente é obrigatorio")
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @Schema(description = "Cliente associado ao contrato")
    private Client client;
}