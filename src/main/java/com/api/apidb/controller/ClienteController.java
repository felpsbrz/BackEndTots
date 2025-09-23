package com.api.apidb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.apidb.model.Cliente;
import com.api.apidb.repository.ClienteRepository;
import com.api.apidb.service.EmailService; // import do service

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendemail")
    public ResponseEntity<String> enviarEmail(
            @RequestParam String para,
            @RequestParam String assunto,
            @RequestParam String mensagem) {
        try {
            emailService.enviarEmail(para, assunto, mensagem);
            return ResponseEntity.ok("E-mail enviado com sucesso para " + para);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar email: " + e.getMessage());
        }
    }

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = clienteRepository.save(cliente);

            // dispara email automático para o email informado no cadastro
            if (clienteSalvo.getEmail() != null && !clienteSalvo.getEmail().isEmpty()) {
                String assunto = "Cadastro realizado com sucesso";
                String mensagem = "Olá " 
                        + (clienteSalvo.getNomeFantasia() != null ? clienteSalvo.getNomeFantasia() : "Cliente")
                        + ",\n\nSeu cadastro foi realizado com sucesso!\n\nObrigado.";
                emailService.enviarEmail(clienteSalvo.getEmail(), assunto, mensagem);
            }

            return ResponseEntity.ok(clienteSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
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
