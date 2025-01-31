package com.example.sinapsis.repositories;

import com.example.sinapsis.model.RedeMT;
import com.example.sinapsis.model.Subestacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RedeMTRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RedeMTRepository redeMTRepository;

    private Subestacao subestacao;
    private RedeMT redeMT;

    @BeforeEach
    public void setUp() {
        // Configuração inicial para os testes
        subestacao = new Subestacao();
        subestacao.setCodigo("SUB001");
        subestacao.setNome("Subestação 1");
        entityManager.persist(subestacao); // Persiste a subestação no banco de dados

        redeMT = new RedeMT();
        redeMT.setCodigo("REDE001");
        redeMT.setNome("Rede 1");
        redeMT.setSubestacao(subestacao);
        entityManager.persist(redeMT); // Persiste a rede MT no banco de dados
        entityManager.flush(); // Força a sincronização com o banco de dados
    }

    @Test
    public void testFindByCodigo() {
        // Execução do método
        Optional<RedeMT> found = redeMTRepository.findByCodigo("REDE001");

        // Verificações
        assertTrue(found.isPresent());
        assertEquals("REDE001", found.get().getCodigo());
        assertEquals("Rede 1", found.get().getNome());
    }

    @Test
    public void testFindByCodigoNotFound() {
        // Execução do método
        Optional<RedeMT> found = redeMTRepository.findByCodigo("REDE999");

        // Verificações
        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByCodigoAndSubestacaoId() {
        // Execução do método
        Optional<RedeMT> found = redeMTRepository.findByCodigoAndSubestacaoId("REDE001", subestacao.getId());

        // Verificações
        assertTrue(found.isPresent());
        assertEquals("REDE001", found.get().getCodigo());
        assertEquals(subestacao.getId(), found.get().getSubestacao().getId());
    }

    @Test
    public void testFindByCodigoAndSubestacaoIdNotFound() {
        // Execução do método
        Optional<RedeMT> found = redeMTRepository.findByCodigoAndSubestacaoId("REDE001", 999);

        // Verificações
        assertFalse(found.isPresent());
    }
}
