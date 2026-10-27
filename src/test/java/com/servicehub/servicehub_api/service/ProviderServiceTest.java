package com.servicehub.servicehub_api.service;

import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.repository.ProviderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Liga o motor do Mockito
class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository; // Banco falso

    @InjectMocks
    private ProviderService providerService; // O Service real que será testado

    @Test
    @DisplayName("deve retornar provider quando buscar por um ID existente")
    void deveRetornarProviderQuandoBuscarPorID() {
        // 1. Arrange (Preparar)
        Provider p = new Provider();
        p.setId(1L);
        p.setName("Juliano");

        // Ensina o banco falso: "Quando alguém pedir o ID 1, devolva o provider Juliano"
        when(providerRepository.findById(1L)).thenReturn(Optional.of(p));

        // 2. Act (Agir)
        Optional<Provider> resultado = providerService.buscarPorId(1L);

        // 3. Assert (Verificar)
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getName()).isEqualTo("Juliano");
        verify(providerRepository, times(1)).findById(1L); // Verifica se o banco foi chamado 1 vez
    }

    @Test
    @DisplayName("deve salvar e retornar um novo provider")
    void deveSalvarProvider() {
        // Arrange
        Provider p = new Provider();
        p.setName("Empresa X");

        // Ensina o banco falso: "Quando mandarem salvar qualquer provider, devolva o mesmo"
        when(providerRepository.save(any(Provider.class))).thenReturn(p);

        // Act
        Provider salvo = providerService.criar(p);

        // Assert
        assertThat(salvo.getName()).isEqualTo("Empresa X");
        verify(providerRepository, times(1)).save(any(Provider.class));
    }
}