package com.servicehub.servicehub_api.controller;

import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.service.ProviderService;
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
@RequestMapping("/providers")
@Tag(
        name = "Prestadores",
        description = "Gerenciamento de prestadores da plataforma ServiceHub"
)
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping
    @Operation(
            summary = "Criar prestador",
            description = "Cadastra um novo prestador na plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Prestador criado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Provider.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos"
            )
    })
    public ResponseEntity<Provider> criar(
            @Valid @RequestBody Provider provider) {

        return ResponseEntity.status(201)
                .body(providerService.criar(provider));
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping
    @Operation(
            summary = "Listar prestadores",
            description = "Retorna todos os prestadores cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Prestadores listados com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Provider.class)
                    )
            )
    })
    public ResponseEntity<List<Provider>> listar() {

        return ResponseEntity.ok(providerService.listar());
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar prestador por ID",
            description = "Retorna os dados de um prestador específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Prestador encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Provider.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Prestador não encontrado"
            )
    })
    public ResponseEntity<Provider> buscarPorId(

            @Parameter(
                    description = "ID único do prestador",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        return providerService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar prestador",
            description = "Atualiza todos os dados de um prestador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Prestador atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Provider.class)
                    )
            )
    })
    public ResponseEntity<Provider> atualizar(

            @Parameter(
                    description = "ID único do prestador",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @Valid @RequestBody Provider provider) {

        return ResponseEntity.ok(
                providerService.atualizar(id, provider)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar parcialmente um prestador",
            description = "Atualiza apenas os campos informados do prestador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Prestador atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Provider.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Prestador não encontrado"
            )
    })
    public ResponseEntity<Provider> atualizarParcial(

            @Parameter(
                    description = "ID único do prestador",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody Map<String, Object> campos) {

        return providerService.atualizarParcial(id, campos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir prestador",
            description = "Remove um prestador da plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Prestador excluído com sucesso"
            )
    })
    public ResponseEntity<Void> deletar(

            @Parameter(
                    description = "ID único do prestador",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        providerService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}