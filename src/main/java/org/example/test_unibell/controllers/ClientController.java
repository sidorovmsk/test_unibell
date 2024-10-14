package org.example.test_unibell.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Добавить нового клиента", description = "Создает нового клиента и сохраняет его в базе данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка запроса"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @Operation(summary = "Получить всех клиентов", description = "Возвращает список всех клиентов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список клиентов успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getClients() {
        return clientService.getClients();
    }

    @Operation(summary = "Получить клиента по ID", description = "Возвращает клиента по заданному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно получен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @Parameter(name = "id", description = "ID клиента", required = true)
    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }
}
