package com.servicehub.servicehub_api.service;

import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;


@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    public Provider criar(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<Provider> listar() {
        return providerRepository.findAll();
    }

    public Optional<Provider> buscarPorId(Long id) {
        return providerRepository.findById(id);
    }

    public Provider atualizar(Long id, Provider provider) {
        provider.setId(id);
        return providerRepository.save(provider);
    }

    public void deletar(Long id) {
        providerRepository.deleteById(id);
    }

    public Optional<Provider> atualizarParcial(Long id, Map<String, Object> campos) {

        Optional<Provider> resultado = providerRepository.findById(id);

        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        Provider provider = resultado.get();

        if (campos.containsKey("name")) {
            provider.setName((String) campos.get("name"));
        }

        if (campos.containsKey("email")) {
            provider.setEmail((String) campos.get("email"));
        }

        if (campos.containsKey("phone")) {
            provider.setPhone((String) campos.get("phone"));
        }

        return Optional.of(providerRepository.save(provider));
    }

}