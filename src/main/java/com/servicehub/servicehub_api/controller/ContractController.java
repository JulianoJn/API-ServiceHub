package com.servicehub.servicehub_api.controller;

import com.servicehub.servicehub_api.model.Contract;
import com.servicehub.servicehub_api.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contracts")
@Tag(
        name = "Contratos",
        description = "Gerenciamento de contratos da plataforma ServiceHub"
)
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    @Operation(
            summary = "Criar contrato",
            description = "Cadastra um novo contrato entre um cliente e um prestador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contrato criado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Contract.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos"
            )
    })
    public ResponseEntity<Contract> criar(
            @RequestBody Contract contract) {

        return ResponseEntity.status(201)
                .body(contractService.criar(contract));
    }

    @GetMapping
    @Operation(
            summary = "Listar contratos",
            description = "Retorna todos os contratos cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contratos listados com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Contract.class)
                    )
            )
    })
    public ResponseEntity<List<Contract>> listar() {

        return ResponseEntity.ok(contractService.listar());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar contrato por ID",
            description = "Retorna os dados de um contrato específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrato encontrado",
                    content = @Content(
                            schema = @Schema(implementation = Contract.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrato não encontrado"
            )
    })
    public ResponseEntity<Contract> buscarPorId(

            @Parameter(
                    description = "ID único do contrato",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        return contractService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar contrato",
            description = "Atualiza os dados de um contrato."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrato atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Contract.class)
                    )
            )
    })
    public ResponseEntity<Contract> atualizar(

            @Parameter(
                    description = "ID único do contrato",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody Contract contract) {

        return ResponseEntity.ok(
                contractService.atualizar(id, contract)
        );
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar parcialmente um contrato",
            description = "Atualiza parcialmente os dados de um contrato."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contrato atualizado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Contract.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contrato não encontrado"
            )
    })
    public ResponseEntity<Contract> atualizarParcial(

            @Parameter(
                    description = "ID único do contrato",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody Map<String, Object> campos) {

        return contractService.atualizarParcial(id, campos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir contrato",
            description = "Remove um contrato da plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Contrato excluído com sucesso"
            )
    })
    public ResponseEntity<Void> deletar(

            @Parameter(
                    description = "ID único do contrato",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        contractService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}