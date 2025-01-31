package com.example.sinapsis.controllers;

import com.example.sinapsis.model.Subestacao;
import com.example.sinapsis.services.SubestacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador responsável por gerenciar operações relacionadas à entidade Subestacao.
 * Expõe endpoints para listar, buscar por ID, criar, atualizar e excluir registros de Subestacao.
 * Inclui tratamento de exceções e logs para garantir que erros sejam registrados e tratados adequadamente.
 */
@RestController
@RequestMapping("/subestacoes")
public class SubestacaoController {
    @Autowired
    private SubestacaoService subestacaoService;

    private static final Logger logger = LoggerFactory.getLogger(SubestacaoController.class);

    /**
     * Retorna uma lista com todas as subestações cadastradas.
     *
     * @return ResponseEntity contendo a lista de subestações (status 200).
     */
    @GetMapping
    public ResponseEntity<List<Subestacao>> getAll() {
        List<Subestacao> subestacoes = subestacaoService.findAll();
        return ResponseEntity.ok(subestacoes);
    }

    /**
     * Busca uma subestação pelo seu ID.
     *
     * @param id ID da subestação a ser buscada.
     * @return ResponseEntity com a subestação encontrada (status 200) ou status 404 se não for encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subestacao> getById(@PathVariable Integer id) {
        Subestacao subestacao = subestacaoService.findById(id);
        return subestacao != null ? ResponseEntity.ok(subestacao) : ResponseEntity.notFound().build();
    }

    /**
     * Cria uma nova subestação.
     *
     * @param subestacao Objeto Subestacao contendo os dados da subestação a ser criada.
     * @return ResponseEntity com a subestação criada (status 200) ou uma mensagem de erro em caso de falha (status 400 ou 500).
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Subestacao subestacao) {
        try {
            Subestacao novaSubestacao = subestacaoService.save(subestacao);
            return ResponseEntity.ok(novaSubestacao);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar subestação: {}", e.getMessage());  // Log de erro
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            logger.error("Erro de integridade ao criar subestação: {}", e.getMessage()); // Log de erro
            return ResponseEntity.badRequest().body("Erro de integridade: Possível duplicação de dados.");
        } catch (Exception e) {
            logger.error("Erro inesperado ao criar subestação: {}", e.getMessage(), e);  // Log de erro com stacktrace
            return ResponseEntity.internalServerError().body("Erro inesperado ao criar subestação.");
        }
    }

    /**
     * Atualiza uma subestação existente.
     *
     * @param id         ID da subestação a ser atualizada.
     * @param subestacao Objeto Subestacao contendo os novos dados da subestação.
     * @return ResponseEntity com a subestação atualizada (status 200) ou uma mensagem de erro em caso de falha (status 400 ou 500).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Subestacao subestacao) {
        try {
            Subestacao updatedSubestacao = subestacaoService.update(id, subestacao);
            return ResponseEntity.ok(updatedSubestacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro de integridade: Verifique os dados informados.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado ao atualizar subestação.");
        }
    }

    /**
     * Exclui uma subestação pelo seu ID.
     *
     * @param id ID da subestação a ser excluída.
     * @return ResponseEntity com uma mensagem de sucesso (status 200) ou uma mensagem de erro em caso de falha (status 400 ou 500).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            subestacaoService.deleteById(id);
            return ResponseEntity.ok("Subestação removida com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro de integridade: Esta subestação pode estar vinculada a outras entidades.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado ao remover subestação.");
        }
    }
}
