package com.example.sinapsis.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

/**
 * Classe que representa a entidade Subestacao no banco de dados.
 * Uma Subestacao contém informações como código, nome, latitude, longitude e uma lista de redes MT associadas.
 */
@Entity
@Table(name = "TB_SUBESTACAO")
@NoArgsConstructor
@AllArgsConstructor
public class Subestacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUBESTACAO")
    private Integer id;

    @NotBlank
    @Column(name = "CODIGO", length = 3, nullable = false, unique = true)
    private String codigo;

    @NotBlank
    @Column(name = "NOME", length = 100)
    private String nome;

    @Column(name = "LATITUDE", precision = 15, scale = 13, nullable = false)
    @NotNull(message = "A latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "Latitude mínima é -90")
    @DecimalMax(value = "90.0", message = "Latitude máxima é 90")
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 15, scale = 13)
    @NotNull(message = "A longitude é obrigatória")
    @DecimalMin(value = "-180.000", message = "Longitude mínima é -180")
    @DecimalMax(value = "180.000", message = "Longitude máxima é 180")
    private BigDecimal longitude;

    @OneToMany(mappedBy = "subestacao", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private List<RedeMT> redesMT;

    /**
     * Retorna o ID da Subestacao.
     *
     * @return ID da Subestacao.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da Subestacao.
     *
     * @param id ID da Subestacao.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o código da Subestacao.
     *
     * @return Código da Subestacao.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o código da Subestacao.
     *
     * @param codigo Código da Subestacao.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o nome da Subestacao.
     *
     * @return Nome da Subestacao.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da Subestacao.
     *
     * @param nome Nome da Subestacao.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a latitude da Subestacao.
     *
     * @return Latitude da Subestacao.
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Define a latitude da Subestacao.
     *
     * @param latitude Latitude da Subestacao.
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * Retorna a longitude da Subestacao.
     *
     * @return Longitude da Subestacao.
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Define a longitude da Subestacao.
     *
     * @param longitude Longitude da Subestacao.
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * Retorna a lista de redes MT associadas à Subestacao.
     *
     * @return Lista de redes MT.
     */
    public List<RedeMT> getRedesMT() {
        return redesMT;
    }

    /**
     * Define a lista de redes MT associadas à Subestacao.
     *
     * @param redesMT Lista de redes MT.
     */
    public void setRedesMT(List<RedeMT> redesMT) {
        this.redesMT = redesMT;
    }
}
