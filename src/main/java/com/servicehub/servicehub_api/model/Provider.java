package com.servicehub.servicehub_api.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do provedor", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nome do provedor", example = "João Silva")
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    @Schema(description = "E-mail do provedor", example = "joao@email.com")
    private String email;

    @Column(nullable = false, length = 20)
    @Schema(description = "Telefone do provedor", example = "62999999999")
    private String phone;
}