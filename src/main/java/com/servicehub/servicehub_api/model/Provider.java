package com.servicehub.servicehub_api.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do provedor", example = "1")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nome do provedor", example = "João Silva")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    @Column(nullable = false, unique = true, length = 150)
    @Schema(description = "E-mail do provedor", example = "joao@email.com")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "O telefone deve ter entre 8 e 20 caracteres")
    @Column(nullable = false, length = 20)
    @Schema(description = "Telefone do provedor", example = "62999999999")
    private String phone;
}