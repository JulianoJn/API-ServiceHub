package com.servicehub.servicehub_api.controller;

import com.servicehub.servicehub_api.model.Client;
import com.servicehub.servicehub_api.model.Contract;
import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.service.ClientService;
import com.servicehub.servicehub_api.service.ContractService;
import com.servicehub.servicehub_api.service.ProviderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ServiceHubGraphQLController {

    private final ProviderService providerService;
    private final ClientService clientService;
    private final ContractService contractService;

    public ServiceHubGraphQLController(ProviderService providerService, ClientService clientService, ContractService contractService) {
        this.providerService = providerService;
        this.clientService = clientService;
        this.contractService = contractService;
    }

    // ================= QUERIES =================

    @QueryMapping
    public List<Provider> providers() {
        return providerService.listar();
    }

    @QueryMapping
    public Provider provider(@Argument Long id) {
        return providerService.buscarPorId(id).orElse(null);
    }

    @QueryMapping
    public List<Client> clients() {
        return clientService.listar();
    }

    @QueryMapping
    public Client client(@Argument Long id) {
        return clientService.buscarPorId(id).orElse(null);
    }

    @QueryMapping
    public List<Contract> contracts() {
        return contractService.listar();
    }

    @QueryMapping
    public Contract contract(@Argument Long id) {
        return contractService.buscarPorId(id).orElse(null);
    }

    // ================= MUTATIONS =================

    @MutationMapping
    public Provider createProvider(@Argument String name, @Argument String email, @Argument String phone) {
        Provider p = new Provider();
        p.setName(name);
        p.setEmail(email);
        p.setPhone(phone);
        return providerService.criar(p);
    }

    @MutationMapping
    public Client createClient(@Argument String name, @Argument String email, @Argument String phone) {
        Client c = new Client();
        c.setName(name);
        c.setEmail(email);
        c.setPhone(phone);
        return clientService.criar(c);
    }

    @MutationMapping
    public Contract createContract(@Argument Long providerId, @Argument Long clientId) {
        Contract c = new Contract();

        Provider p = new Provider();
        p.setId(providerId);
        c.setProvider(p);

        Client cli = new Client();
        cli.setId(clientId);
        c.setClient(cli);

        return contractService.criar(c);
    }
}