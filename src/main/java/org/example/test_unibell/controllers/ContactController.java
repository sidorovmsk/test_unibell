package org.example.test_unibell.controllers;

import lombok.RequiredArgsConstructor;
import org.example.test_unibell.dtos.ContactDTO;
import org.example.test_unibell.models.Contact;
import org.example.test_unibell.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<Contact> addContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactService.addContactForClient(contactDTO.getClientId(), contactDTO.getTypeName(), contactDTO.getContactValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(contact);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Contact>> getContactsByClientId(@PathVariable Long clientId) {
        List<Contact> contacts = contactService.getContactsByClientId(clientId);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/client/{clientId}/type/{typeName}")
    public ResponseEntity<List<Contact>> getContactsByClientIdAndType(@PathVariable Long clientId, @PathVariable String typeName) {
        List<Contact> contacts = contactService.getContactsByClientIdAndType(clientId, typeName);
        return ResponseEntity.ok(contacts);
    }
}
