<<<<<<< HEAD
package com.api.apidb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.apidb.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // É isso! O Spring Data JPA cria os métodos CRUD automaticamente.
    // (findAll, findById, save, deleteById, etc.)

     @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.razao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(c.nomeFantasia) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(c.cnpj) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Cliente> buscarClientePersonalizado(@Param("termo") String termo);
}
=======
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

>>>>>>> e593a11be74af1258716b014267c1ace998eef6a
