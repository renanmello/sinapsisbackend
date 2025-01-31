package com.example.sinapsis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

/**
 * Classe que representa a entidade RedeMT no banco de dados.
 * Uma RedeMT está associada a uma Subestacao e contém informações como código, nome e tensão nominal.
 */
@Entity
@Table(name = "TB_REDE_MT")
@NoArgsConstructor
@AllArgsConstructor
public class RedeMT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REDE_MT")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_SUBESTACAO", nullable = false)
    @JsonBackReference
    private Subestacao subestacao;

    @Column(name = "CODIGO", length = 5, nullable = false, unique = true)
    private String codigo;

    @Column(name = "NOME", length = 100)
    private String nome;

    @DecimalMin(value = "1.0", message = "Tensão nominal mínima é 1.0")
    @DecimalMax(value = "500.0", message = "Tensão nominal máxima é 500.0")
    @Column(name = "TENSAO_NOMINAL", precision = 5, scale = 2)
    private BigDecimal tensaoNominal;

    /**
     * Retorna o ID da RedeMT.
     *
     * @return ID da RedeMT.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da RedeMT.
     *
     * @param id ID da RedeMT.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna a Subestacao associada à RedeMT.
     *
     * @return Subestacao associada.
     */
    public Subestacao getSubestacao() {
        return subestacao;
    }

    /**
     * Define a Subestacao associada à RedeMT.
     *
     * @param subestacao Subestacao a ser associada.
     */
    public void setSubestacao(Subestacao subestacao) {
        this.subestacao = subestacao;
    }

    /**
     * Retorna o código da RedeMT.
     *
     * @return Código da RedeMT.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o código da RedeMT.
     *
     * @param codigo Código da RedeMT.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna a tensão nominal da RedeMT.
     *
     * @return Tensão nominal da RedeMT.
     */
    public BigDecimal getTensaoNominal() {
        return tensaoNominal;
    }

    /**
     * Define a tensão nominal da RedeMT.
     *
     * @param tensaoNominal Tensão nominal da RedeMT.
     */
    public void setTensaoNominal(BigDecimal tensaoNominal) {
        this.tensaoNominal = tensaoNominal;
    }

    /**
     * Retorna o nome da RedeMT.
     *
     * @return Nome da RedeMT.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da RedeMT.
     *
     * @param nome Nome da RedeMT.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}

