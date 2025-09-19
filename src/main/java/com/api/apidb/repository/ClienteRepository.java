package com.api.apidb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apidb.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    
     @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.razao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(c.nomeFantasia) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(c.cnpj) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Cliente> buscarClientePersonalizado(@Param("termo") String termo);

}

