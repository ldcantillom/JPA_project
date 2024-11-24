package org.example.airport.model.services;

import org.example.airport.model.dtos.ClientDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientDto save(ClientDto client);
    Optional<ClientDto> findById(int id);
    Optional<ClientDto> update(int id, ClientDto client);
    List<ClientDto> findAll();
    List<ClientDto> findByName(String name);
    void deleteById(int id);
}
