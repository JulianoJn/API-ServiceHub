package com.servicehub.servicehub_api.service;

import com.servicehub.servicehub_api.model.Client;
import com.servicehub.servicehub_api.model.Contract;
import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.repository.ClientRepository;
import com.servicehub.servicehub_api.repository.ContractRepository;
import com.servicehub.servicehub_api.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ProviderRepository providerRepository;
    private final ClientRepository clientRepository;

    public ContractService(
            ContractRepository contractRepository,
            ProviderRepository providerRepository,
            ClientRepository clientRepository) {

        this.contractRepository = contractRepository;
        this.providerRepository = providerRepository;
        this.clientRepository = clientRepository;
    }

    public Contract criar(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> listar() {
        return contractRepository.findAll();
    }

    public Optional<Contract> buscarPorId(Long id) {
        return contractRepository.findById(id);
    }

    public Contract atualizar(Long id, Contract contract) {
        contract.setId(id);
        return contractRepository.save(contract);
    }

    public void deletar(Long id) {
        contractRepository.deleteById(id);
    }

    public Optional<Contract> atualizarParcial(
            Long id,
            Map<String, Object> campos) {

        Optional<Contract> resultado = contractRepository.findById(id);

        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        Contract contract = resultado.get();

        if (campos.containsKey("providerId")) {

            Long providerId =
                    ((Number) campos.get("providerId")).longValue();

            Provider provider = providerRepository.findById(providerId)
                    .orElseThrow();

            contract.setProvider(provider);
        }

        if (campos.containsKey("clientId")) {

            Long clientId =
                    ((Number) campos.get("clientId")).longValue();

            Client client = clientRepository.findById(clientId)
                    .orElseThrow();

            contract.setClient(client);
        }

        return Optional.of(contractRepository.save(contract));
    }
}