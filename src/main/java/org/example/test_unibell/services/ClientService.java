package org.example.test_unibell.services;

import lombok.RequiredArgsConstructor;
import org.example.test_unibell.models.Client;
import org.example.test_unibell.repositories.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ResponseEntity<Client> addClient(Client client) {
        return ResponseEntity.ok(clientRepository.save(client));
    }

    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
}
