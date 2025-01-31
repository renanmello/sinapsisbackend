package com.example.sinapsis.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Serviço responsável por gerar e validar tokens JWT (JSON Web Tokens).
 * Utiliza a biblioteca Auth0 JWT para criar tokens com tempo de expiração e validar tokens existentes.
 */
@Service
public class JwtService {
    // Chave secreta usada para assinar e verificar tokens JWT,
    // Deve ser armazenada em variáveis de ambiente(.env) e
    // referenciada no arquivo de propriedades para segurança da aplicação.
    private static final String SECRET_KEY = "teste_chave";

    // Tempo de expiração do token (2 horas em milissegundos)
    private static final long EXPIRATION_TIME = 7200000;

    // Algoritmo usado para assinar e verificar tokens JWT
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Gera um token JWT para o usuário especificado.
     *
     * @param username Nome de usuário a ser incluído no token como subject.
     * @return Token JWT gerado.
     */
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)// Define o subject do token (nome de usuário)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// Define o tempo de expiração
                .sign(algorithm);// Assina o token com o algoritmo HMAC256
    }

    /**
     * Valida um token JWT e retorna o nome de usuário contido no token.
     *
     * @param token Token JWT a ser validado.
     * @return Nome de usuário (subject) contido no token, ou null se o token for inválido ou expirado.
     */
    public String validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();// Cria um verificador de token
            DecodedJWT decodedJWT = verifier.verify(token);// Verifica e decodifica o token
            return decodedJWT.getSubject(); // Retorna o subject (nome de usuário) do token
        } catch (JWTVerificationException e) {
            return null; // Retorna null se o token for inválido ou expirado
        }
    }
}
