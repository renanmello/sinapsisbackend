package com.example.sinapsis.repositories;

import com.example.sinapsis.model.RedeMT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repositório para a entidade RedeMT.
 * Fornece métodos para acessar e manipular dados de redes MT no banco de dados.
 * Estende JpaRepository, que inclui métodos CRUD básicos.
 */
@Repository
public interface RedeMTRepository extends JpaRepository<RedeMT, Integer> {
    /**
     * Busca uma RedeMT pelo código.
     *
     * @param codigo Código da RedeMT a ser buscada.
     * @return Um Optional contendo a RedeMT encontrada, ou vazio se não for encontrada.
     */
    Optional<RedeMT> findByCodigo(String codigo);

    /**
     * Busca uma RedeMT pelo código e pelo ID da Subestacao associada.
     *
     * @param codigo       Código da RedeMT a ser buscada.
     * @param subestacaoId ID da Subestacao associada à RedeMT.
     * @return Um Optional contendo a RedeMT encontrada, ou vazio se não for encontrada.
     */
    Optional<RedeMT> findByCodigoAndSubestacaoId(String codigo, Integer subestacaoId);
}
