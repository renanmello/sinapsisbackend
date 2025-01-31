package com.example.sinapsis.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro responsável por interceptar todas as requisições HTTP e validar o token JWT presente no cabeçalho "Authorization".
 * Se o token for válido, o usuário é autenticado no contexto de segurança do Spring.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /**
     * Construtor da classe JwtAuthenticationFilter.
     *
     * @param jwtService Serviço responsável por validar tokens JWT.
     */
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Método que implementa a lógica do filtro.
     * Extrai o token JWT do cabeçalho "Authorization", valida o token e autentica o usuário no contexto de segurança.
     *
     * @param request  Requisição HTTP.
     * @param response Resposta HTTP.
     * @param chain    Cadeia de filtros a ser executada.
     * @throws ServletException Em caso de erro durante o processamento da requisição.
     * @throws IOException      Em caso de erro de I/O.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extrai o cabeçalho "Authorization"
        String header = request.getHeader("Authorization");
        // Verifica se o cabeçalho está presente e começa com "Bearer "
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response); // Passa a requisição para o próximo filtro
            return;
        }

        // Extrai o token JWT (remove o prefixo "Bearer ")
        String token = header.substring(7);
        // Valida o token e obtém o nome de usuário
        String username = jwtService.validateToken(token);

        // Se o token for válido, autentica o usuário no contexto de segurança
        if (username != null) {
            User user = new User(username, "", Collections.emptyList());// Cria um objeto User com o nome de usuário
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());// Cria a autenticação
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Define os detalhes da autenticação
            SecurityContextHolder.getContext().setAuthentication(authentication);// Define a autenticação no contexto de segurança
        }

        // Passa a requisição para o próximo filtro
        chain.doFilter(request, response);
    }
}