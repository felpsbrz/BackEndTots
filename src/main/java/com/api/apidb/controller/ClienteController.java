package com.api.apidb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.apidb.model.Cliente;
import com.api.apidb.repository.ClienteRepository;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;



    
@PostMapping
public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
    try {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    } catch (Exception e) {
        return ResponseEntity.status(500).build(); // ou incluir mensagem: .body(null)
    }
}




    @GetMapping
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }





    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElse(ResponseEntity.notFound().build());
    }



@GetMapping("/buscar")
public List<Cliente> buscarClientePersonalizado(@RequestParam("nome_fantasia") String termo) {
    return clienteRepository.buscarClientePersonalizado(termo);
}

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente detalhesCliente) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
      
                    clienteExistente.setCodigo(detalhesCliente.getCodigo());
                    clienteExistente.setLoja(detalhesCliente.getLoja());
                    clienteExistente.setRazao(detalhesCliente.getRazao());
                    clienteExistente.setTipo(detalhesCliente.getTipo());
                    clienteExistente.setNomeFantasia(detalhesCliente.getNomeFantasia());
                    clienteExistente.setFinalidade(detalhesCliente.getFinalidade());
                    clienteExistente.setCnpj(detalhesCliente.getCnpj());
                    clienteExistente.setCep(detalhesCliente.getCep());
                    clienteExistente.setPais(detalhesCliente.getPais());
                    clienteExistente.setEstado(detalhesCliente.getEstado());
                    clienteExistente.setCodMunicipio(detalhesCliente.getCodMunicipio());
                    clienteExistente.setCidade(detalhesCliente.getCidade());
                    clienteExistente.setEndereco(detalhesCliente.getEndereco());
                    clienteExistente.setBairro(detalhesCliente.getBairro());
                    clienteExistente.setDdd(detalhesCliente.getDdd());
                    clienteExistente.setTelefone(detalhesCliente.getTelefone());
                    clienteExistente.setAbertura(detalhesCliente.getAbertura());
                    clienteExistente.setContato(detalhesCliente.getContato());
                    clienteExistente.setEmail(detalhesCliente.getEmail());
                    clienteExistente.setHomepage(detalhesCliente.getHomepage());
                    // --- Fim do bloco atualizado ---

                    Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
                    return ResponseEntity.ok().body(clienteAtualizado);
                }).orElse(ResponseEntity.notFound().build());
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
