package org.example.test_unibell.repositories;

import org.example.test_unibell.models.Contact;
import org.example.test_unibell.models.ContactType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByClientId(Long clientId, Pageable pageable);
    List<Contact> findByClientIdAndType(Long clientId, ContactType type, Pageable pageable);
}
