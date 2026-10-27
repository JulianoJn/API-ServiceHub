package com.servicehub.servicehub_api.service;

import com.servicehub.servicehub_api.model.Client;
import com.servicehub.servicehub_api.model.Provider;
import com.servicehub.servicehub_api.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client criar(Client client) {
        sanitizar(client);
        return clientRepository.save(client);
    }

    public List<Client> listar() {
        return clientRepository.findAll();
    }

    public Optional<Client> buscarPorId(Long id) {
        return clientRepository.findById(id);
    }

    public Client atualizar(Long id, Client client) {
        sanitizar(client);
        client.setId(id);
        return clientRepository.save(client);
    }

    public void deletar(Long id) {
        clientRepository.deleteById(id);
    }

    public Optional<Client> atualizarParcial(Long id, Map<String, Object> campos) {



        Optional<Client> resultado = clientRepository.findById(id);


        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        Client client = resultado.get();

        if (campos.containsKey("name")) {
            client.setName((String) campos.get("name"));
        }

        if (campos.containsKey("email")) {
            client.setEmail((String) campos.get("email"));
        }

        if (campos.containsKey("phone")) {
            client.setPhone((String) campos.get("phone"));
        }

        sanitizar(client);

        return Optional.of(clientRepository.save(client));
    }

    //  metodo de sanitizar
    private void sanitizar(Client client) {
        if (client.getName() != null) {
            // Remove espaços nas pontas e converte <, >, " em entidades seguras (evita XSS)
            client.setName(HtmlUtils.htmlEscape(client.getName().trim()));
        }
        if (client.getEmail() != null) {
            client.setEmail(client.getEmail().trim().toLowerCase());
        }
        if (client.getPhone() != null) {
            client.setPhone(client.getPhone().trim());
        }
    }

}