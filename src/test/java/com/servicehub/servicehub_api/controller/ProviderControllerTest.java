package com.servicehub.servicehub_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.security.JwtService;
import com.servicehub.servicehub_api.service.CustomUserDetailsService;
import com.servicehub.servicehub_api.service.ProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProviderController.class) // Sobe apenas a camada web (Controllers)
@AutoConfigureMockMvc(addFilters = false) // Desliga a barreira do Spring Security temporariamente para facilitar o teste focado
class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc; // O nosso "Postman" interno

    @MockitoBean
    private ProviderService providerService; // Finge o Service

    // Precisamos mockar esses dois também para o Spring Security não reclamar que faltam dependências
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private ObjectMapper objectMapper = new ObjectMapper(); // Transforma objeto Java em JSON

    @Test
    @DisplayName("GET /providers deve retornar status 200 e a lista em JSON")
    void deveRetornarListaDeProviders() throws Exception {
        // Arrange: Prepara o Service falso para devolver uma lista
        Provider p = new Provider();
        p.setId(1L);
        p.setName("João Silva");
        when(providerService.listar()).thenReturn(List.of(p));

        // Act & Assert: Faz a chamada HTTP e verifica a resposta no mesmo bloco
        mockMvc.perform(get("/providers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Confere se deu 200
                .andExpect(jsonPath("$[0].name").value("João Silva")); // Confere se o nome no JSON está certo
    }

    @Test
    @DisplayName("POST /providers deve retornar 400 Bad Request se o nome for vazio")
    void deveRetornar400QuandoNomeVazio() throws Exception {
        // Arrange: Monta um Provider inválido (sem nome)
        Provider invalido = new Provider();
        invalido.setName(""); // Isso deveria acionar a proteção do @Valid (Aula 08)
        invalido.setEmail("joao@email.com");
        invalido.setPhone("12345678");

        // Transforma nosso objeto falho em uma String JSON
        String json = objectMapper.writeValueAsString(invalido);

        // Act & Assert
        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest()) // Confere se deu erro 400
                .andExpect(jsonPath("$.erros").exists()); // Confere se o GlobalExceptionHandler jogou "erros" na tela
    }
}