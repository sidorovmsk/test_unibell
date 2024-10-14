package org.example.test_unibell.repositories;

import org.example.test_unibell.enums.EContactType;
import org.example.test_unibell.models.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    Optional<ContactType> findByTypeName(EContactType typeName);
}
