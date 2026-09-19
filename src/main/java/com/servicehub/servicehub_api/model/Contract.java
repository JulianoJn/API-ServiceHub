package com.servicehub.servicehub_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do contrato", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    @Schema(description = "Provedor associado ao contrato")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @Schema(description = "Cliente associado ao contrato")
    private Client client;
}