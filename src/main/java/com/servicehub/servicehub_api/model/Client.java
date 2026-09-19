package com.servicehub.servicehub_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do cliente", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nome do cliente", example = "Maria Souza")
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    @Schema(description = "E-mail do cliente", example = "maria@email.com")
    private String email;

    @Column(nullable = false, length = 20)
    @Schema(description = "Telefone do cliente", example = "62988888888")
    private String phone;
}