package com.servicehub.servicehub_api.controller;

import com.servicehub.servicehub_api.model.Client;
import com.servicehub.servicehub_api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
@Tag(
        name = "Clientes",
        description = "Gerenciamento de clientes da plataforma ServiceHub"
)
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @PostMapping
    @Operation(
            summary = "Criar cliente",
            description = "Cadastra um novo cliente na plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Client.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos"
            )
    })
    public ResponseEntity<Client> criar(
           @Valid @RequestBody Client client) {

        return ResponseEntity.status(201)
                .body(clientService.criar(client));
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping
    @Operation(
            summary = "Listar clientes",
            description = "Retorna todos os clientes cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes listados com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Client.class)
                    )
            )
    })
    public ResponseEntity<List<Client>> listar() {

        return ResponseEntity.ok(clientService.listar());
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna os dados de um cliente específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Client.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            )
    })
    public ResponseEntity<Client> buscarPorId(

            @Parameter(
                    description = "ID único do cliente",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        return clientService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza todos os dados de um cliente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Client.class)
                    )
            )
    })
    public ResponseEntity<Client> atualizar(

            @Parameter(
                    description = "ID único do cliente",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody Client client) {

        return ResponseEntity.ok(
                clientService.atualizar(id, client)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar parcialmente um cliente",
            description = "Atualiza apenas os campos informados do cliente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Client.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            )
    })
    public ResponseEntity<Client> atualizarParcial(

            @Parameter(
                    description = "ID único do cliente",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos) {

        return clientService.atualizarParcial(id, campos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir cliente",
            description = "Remove um cliente da plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente excluído com sucesso"
            )
    })
    public ResponseEntity<Void> deletar(

            @Parameter(
                    description = "ID único do cliente",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        clientService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}