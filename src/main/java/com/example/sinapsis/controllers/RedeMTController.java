package com.example.sinapsis.controllers;

import com.example.sinapsis.model.RedeMT;
import com.example.sinapsis.model.Subestacao;
import com.example.sinapsis.services.RedeMTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar operações relacionadas à entidade RedeMT.
 * Expõe endpoints para listar, buscar por ID, criar e excluir registros de RedeMT.
 */
@RestController
@RequestMapping("/redesmt")
public class RedeMTController {
    @Autowired
    private RedeMTService redeMTService;

    /**
     * Retorna uma lista com todas as redes MT cadastradas.
     *
     * @return Lista de objetos RedeMT.
     */
    @GetMapping
    public List<RedeMT> getAll() {
        return redeMTService.findAll();
    }

    /**
     * Busca uma rede MT pelo seu ID.
     *
     * @param id ID da rede MT a ser buscada.
     * @return ResponseEntity com a rede MT encontrada (status 200) ou status 404 se não for encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RedeMT> getById(@PathVariable Integer id) {
        RedeMT redemt = redeMTService.findById(id);

        if (redemt != null) {
            return ResponseEntity.ok(redemt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cria uma nova rede MT.
     *
     * @param redeMT Objeto RedeMT contendo os dados da rede a ser criada.
     * @return Objeto RedeMT criado e salvo.
     */
    @PostMapping
    public RedeMT create(@RequestBody RedeMT redeMT) {
        return redeMTService.save(redeMT);
    }

    /**
     * Exclui uma rede MT pelo seu ID.
     *
     * @param id ID da rede MT a ser excluída.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        redeMTService.deleteById(id);
    }
}
