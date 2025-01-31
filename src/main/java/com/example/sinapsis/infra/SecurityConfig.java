package com.example.sinapsis.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Classe de configuração de segurança da aplicação.
 * Configura a proteção de endpoints, autenticação baseada em JWT, política de sessão e CORS.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Construtor da classe SecurityConfig.
     *
     * @param jwtAuthenticationFilter Filtro responsável por validar tokens JWT e autenticar usuários.
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura a cadeia de filtros de segurança.
     *
     * @param http Configuração de segurança HTTP.
     * @return SecurityFilterChain configurado.
     * @throws Exception Em caso de erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable()) // desabilita o cors (somente pra desenvolvimento)
                .csrf(csrf -> csrf.disable())// Desabilita a proteção CSRF (não recomendado para aplicações web tradicionais)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/subestacoes/**", "/redesmt/**").authenticated()// Protege os endpoints de subestações e redes MT
                        .anyRequest().permitAll()// Permite acesso a todos os outros endpoints sem autenticação
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Configura a política de sessão como STATELESS
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT antes do filtro de autenticação padrão

        return http.build();
    }

    /**
     * Configura o codificador de senhas (BCrypt).
     *
     * @return Instância de PasswordEncoder usando BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o gerenciador de autenticação.
     *
     * @param authenticationConfiguration Configuração de autenticação.
     * @return Instância de AuthenticationManager.
     * @throws Exception Em caso de erro durante a configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura o filtro CORS para permitir requisições de origens específicas.
     *
     * @return Instância de CorsFilter configurada.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);// Permite credenciais (cookies, headers de autenticação)
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));// Permite requisições do frontend (Nessa caso Vue3)
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Métodos HTTP permitidos
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));// Headers permitidos
        source.registerCorsConfiguration("/**", config); // Aplica a configuração CORS a todos os endpoints
        return new CorsFilter(source);
    }
}
