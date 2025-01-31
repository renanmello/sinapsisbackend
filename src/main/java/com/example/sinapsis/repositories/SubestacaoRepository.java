package com.example.sinapsis.repositories;


import com.example.sinapsis.model.Subestacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade Subestacao.
 * Fornece métodos para acessar e manipular dados de subestações no banco de dados.
 * Estende JpaRepository, que inclui métodos CRUD básicos.
 */
@Repository
public interface SubestacaoRepository extends JpaRepository<Subestacao, Integer> {

    /**
     * Verifica se uma Subestacao já existe no banco de dados pelo código.
     *
     * @param codigo Código da Subestacao a ser verificada.
     * @return true se a Subestacao já existe, false caso contrário.
     */
    boolean existsByCodigo(String codigo);
}
