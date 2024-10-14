package org.example.test_unibell.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.test_unibell.dtos.ContactDTO;
import org.example.test_unibell.dtos.ContactResponseDTO;
import org.example.test_unibell.models.Contact;
import org.example.test_unibell.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @Operation(summary = "Добавить новый контакт для клиента", description = "Создает новый контакт для клиента на основе ID клиента, типа контакта и значения.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Контакт успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка запроса"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/add")
    public ResponseEntity<Contact> addContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactService.addContactForClient(contactDTO.getClientId(), contactDTO.getTypeName(), contactDTO.getContactValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(contact);
    }

    @Operation(summary = "Получить все контакты клиента", description = "Возвращает список всех контактов для заданного клиента по ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список контактов успешно получен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @Parameters({
            @Parameter(name = "clientId", description = "ID клиента", required = true),
            @Parameter(name = "page", description = "Номер страницы"),
            @Parameter(name = "size", description = "Размер страницы")
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContactResponseDTO>> getContactsByClientId(@PathVariable Long clientId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        List<Contact> contacts = contactService.getContactsByClientId(clientId, page, size);
        List<ContactResponseDTO> response = contacts.stream()
                .map(contact -> new ContactResponseDTO(contact.getId(), contact.getType().toString(), contact.getContactValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить контакты клиента по типу", description = "Возвращает список контактов для клиента по заданному типу контакта и ID клиента.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список контактов успешно получен"),
            @ApiResponse(responseCode = "404", description = "Клиент или контакты не найдены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @Parameters({
            @Parameter(name = "clientId", description = "ID клиента", required = true),
            @Parameter(name = "typeName", description = "Тип контакта", required = true)
    })
    @GetMapping("/client/{clientId}/type/{typeName}")
    public ResponseEntity<List<ContactResponseDTO>> getContactsByClientIdAndType(@PathVariable Long clientId,
                                                                                 @PathVariable String typeName,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        List<Contact> contacts = contactService.getContactsByClientIdAndType(clientId, typeName, page, size);
        List<ContactResponseDTO> response = contacts.stream()
                .map(contact -> new ContactResponseDTO(contact.getId(), contact.getType().toString(), contact.getContactValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
