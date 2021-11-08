package com.example.demo.repositories;

import com.example.demo.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    List<Client> findAll();

    Client findClientById(Integer id);

    List<Client> findClientsByFirstNameContainsAndLastNameContains(String firstName, String lastName);
}