package org.example.test_unibell.services;

import lombok.RequiredArgsConstructor;
import org.example.test_unibell.enums.EContactType;
import org.example.test_unibell.models.Client;
import org.example.test_unibell.models.Contact;
import org.example.test_unibell.models.ContactType;
import org.example.test_unibell.repositories.ClientRepository;
import org.example.test_unibell.repositories.ContactRepository;
import org.example.test_unibell.repositories.ContactTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ClientRepository clientRepository;

    public Contact addContactForClient(Long clientId, String typeName, String contactValue) {
        ContactType contactType = contactTypeRepository.findByTypeName(EContactType.valueOf(typeName))
                .orElseThrow(() -> new RuntimeException("Invalid contact type"));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Contact contact = new Contact();
        contact.setClient(client);
        contact.setType(contactType);
        contact.setContactValue(contactValue);

        return contactRepository.save(contact);
    }

    public List<Contact> getContactsByClientId(Long clientId) {
        return contactRepository.findByClientId(clientId);
    }

    public List<Contact> getContactsByClientIdAndType(Long clientId, String typeName) {
        ContactType contactType = contactTypeRepository.findByTypeName(EContactType.valueOf(typeName))
                .orElseThrow(() -> new RuntimeException("Invalid contact type"));
        return contactRepository.findByClientIdAndType(clientId, contactType);
    }

}
