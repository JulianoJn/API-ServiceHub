package com.servicehub.servicehub_api.service;

import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

        sanitizar(provider);
        return providerRepository.save(provider);
    }

    public List<Provider> listar() {
        return providerRepository.findAll();
    }

    public Optional<Provider> buscarPorId(Long id) {
        return providerRepository.findById(id);
    }

    public Provider atualizar(Long id, Provider provider) {
        sanitizar(provider);
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

        sanitizar(provider);
        return Optional.of(providerRepository.save(provider));
    }

    //  metodo de sanitizar
    private void sanitizar(Provider provider) {
        if (provider.getName() != null) {
            // Remove espaços nas pontas e converte <, >, " em entidades seguras (evita XSS)
            provider.setName(HtmlUtils.htmlEscape(provider.getName().trim()));
        }
        if (provider.getEmail() != null) {
            provider.setEmail(provider.getEmail().trim().toLowerCase());
        }
        if (provider.getPhone() != null) {
            provider.setPhone(provider.getPhone().trim());
        }
    }

}