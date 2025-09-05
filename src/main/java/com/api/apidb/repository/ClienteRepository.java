package com.api.apidb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apidb.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // É isso! O Spring Data JPA cria os métodos CRUD automaticamente.
    // (findAll, findById, save, deleteById, etc.)
}