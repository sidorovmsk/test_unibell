package org.example.test_unibell.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_unibell.models.Client;
import org.example.test_unibell.repositories.ClientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public ResponseEntity<Client> addClient(Client client) {
        log.info("Adding new client: {}", client);
        return ResponseEntity.ok(clientRepository.save(client));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Client>> getClients(int page, int size) {
        log.info("Fetching clients, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(clientRepository.findAll(pageable).getContent());
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        log.info("Fetching client by id: {}", id);
        return clientRepository.findById(id);
    }
}
