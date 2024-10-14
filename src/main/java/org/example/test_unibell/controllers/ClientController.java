package org.example.test_unibell.controllers;

import lombok.RequiredArgsConstructor;
import org.example.test_unibell.models.Client;
import org.example.test_unibell.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }
}
