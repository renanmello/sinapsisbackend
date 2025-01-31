package com.example.sinapsis.controllers;

import com.example.sinapsis.infra.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador responsável por lidar com autenticação de usuários.
 * Expõe endpoints para login e geração de tokens JWT.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;

    /**
     * Construtor da classe AuthController.
     *
     * @param jwtService Serviço responsável por gerar e validar tokens JWT.
     */
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Endpoint para autenticação de usuários.
     * Recebe as credenciais (username e password) e retorna um token JWT se as credenciais forem válidas.
     *
     * @param credentials Mapa contendo as credenciais do usuário (username e password).
     * @return ResponseEntity com o token JWT em caso de sucesso, ou uma mensagem de erro em caso de falha.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Simulação de autenticação. Aqui,  pode validar no banco de dados
        // mas pra simplificar nao salvamos o usuario no banco. so as tabelas
        // subestacoes e redemt como pedido na questao.
        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}
