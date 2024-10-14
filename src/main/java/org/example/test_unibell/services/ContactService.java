package org.example.test_unibell.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_unibell.enums.EContactType;
import org.example.test_unibell.exeptions.ClientNotFoundException;
import org.example.test_unibell.exeptions.InvalidContactTypeException;
import org.example.test_unibell.models.Client;
import org.example.test_unibell.models.Contact;
import org.example.test_unibell.models.ContactType;
import org.example.test_unibell.repositories.ClientRepository;
import org.example.test_unibell.repositories.ContactRepository;
import org.example.test_unibell.repositories.ContactTypeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Contact addContactForClient(Long clientId, String typeName, String contactValue) {
        log.info("Adding contact for clientId: {}, typeName: {}", clientId, typeName);
        ContactType contactType = contactTypeRepository.findByTypeName(EContactType.valueOf(typeName))
                .orElseThrow(() -> new InvalidContactTypeException("Invalid contact type"));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        Contact contact = new Contact();
        contact.setClient(client);
        contact.setType(contactType);
        contact.setContactValue(contactValue);

        return contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public List<Contact> getContactsByClientId(Long clientId, int page, int size) {
        log.info("Fetching contacts for clientId: {}, page: {}, size: {}", clientId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return contactRepository.findByClientId(clientId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Contact> getContactsByClientIdAndType(Long clientId, String typeName, int page, int size) {
        log.info("Fetching contacts for clientId: {}, typeName: {}, page: {}, size: {}", clientId, typeName, page, size);
        Pageable pageable = PageRequest.of(page, size);
        ContactType contactType = contactTypeRepository.findByTypeName(EContactType.valueOf(typeName))
                .orElseThrow(() -> new InvalidContactTypeException("Invalid contact type"));
        return contactRepository.findByClientIdAndType(clientId, contactType, pageable);
    }

}
