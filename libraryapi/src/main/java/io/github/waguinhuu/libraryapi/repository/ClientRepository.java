package io.github.waguinhuu.libraryapi.repository;

import io.github.waguinhuu.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findByClientId(String clientId);
}
