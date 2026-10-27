package com.servicehub.servicehub_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do cliente", example = "1")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100, message ="O nome deve ter entre 2 e 100 caracteres" )
    @Column(nullable = false, length = 100)
    @Schema(description = "Nome do cliente", example = "Maria Souza")
    private String name;


    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    @Column(nullable = false, unique = true, length = 150)
    @Schema(description = "E-mail do cliente", example = "maria@email.com")
    private String email;


    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(description = "Telefone do cliente", example = "62988888888")
    private String phone;
}