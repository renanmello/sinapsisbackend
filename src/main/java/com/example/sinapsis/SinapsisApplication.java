package com.example.sinapsis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * Responsável por iniciar a aplicação e configurar o contexto do Spring.
 */
@SpringBootApplication
public class SinapsisApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args Argumentos passados para a aplicação (geralmente via linha de comando).
     */
    public static void main(String[] args) {
        SpringApplication.run(SinapsisApplication.class, args);
    }

}
