package com.example.sinapsis.services;

import com.example.sinapsis.model.RedeMT;
import com.example.sinapsis.model.Subestacao;
import com.example.sinapsis.repositories.RedeMTRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedeMTServiceTest {
    @Mock
    private RedeMTRepository redeMTRepository;

    @InjectMocks
    private RedeMTService redeMTService;

    private RedeMT redeMT;
    private Subestacao subestacao;

    @BeforeEach
    public void setUp() {
        // Configuração inicial para os testes
        subestacao = new Subestacao();
        subestacao.setId(1);
        subestacao.setCodigo("SUB001");

        redeMT = new RedeMT();
        redeMT.setId(1);
        redeMT.setCodigo("REDE001");
        redeMT.setNome("Rede 1");
        redeMT.setSubestacao(subestacao);
    }

    @Test
    public void testFindAll() {
        // Configuração do mock
        when(redeMTRepository.findAll()).thenReturn(Arrays.asList(redeMT));

        // Execução do método
        List<RedeMT> result = redeMTService.findAll();

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(redeMT, result.get(0));

        // Verifica se o método do repositório foi chamado
        verify(redeMTRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Configuração do mock
        when(redeMTRepository.findById(1)).thenReturn(Optional.of(redeMT));

        // Execução do método
        RedeMT result = redeMTService.findById(1);

        // Verificações
        assertNotNull(result);
        assertEquals(redeMT, result);

        // Verifica se o método do repositório foi chamado
        verify(redeMTRepository, times(1)).findById(1);
    }

    @Test
    public void testFindByIdNotFound() {
        // Configuração do mock
        when(redeMTRepository.findById(1)).thenReturn(Optional.empty());

        // Execução e verificação da exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> redeMTService.findById(1));
        assertEquals("RedeMT not found", exception.getMessage());

        // Verifica se o método do repositório foi chamado
        verify(redeMTRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Configuração do mock
        when(redeMTRepository.findByCodigoAndSubestacaoId("REDE001", 1)).thenReturn(Optional.empty());
        when(redeMTRepository.save(redeMT)).thenReturn(redeMT);

        // Execução do método
        RedeMT result = redeMTService.save(redeMT);

        // Verificações
        assertNotNull(result);
        assertEquals(redeMT, result);

        // Verifica se os métodos do repositório foram chamados
        verify(redeMTRepository, times(1)).findByCodigoAndSubestacaoId("REDE001", 1);
        verify(redeMTRepository, times(1)).save(redeMT);
    }

    @Test
    public void testSaveWithDuplicateCodigo() {
        // Configuração do mock
        when(redeMTRepository.findByCodigoAndSubestacaoId("REDE001", 1)).thenReturn(Optional.of(redeMT));

        // Execução e verificação da exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> redeMTService.save(redeMT));
        assertEquals("Rede já cadastrada com esse código para esta subestação: REDE001", exception.getMessage());

        // Verifica se o método do repositório foi chamado
        verify(redeMTRepository, times(1)).findByCodigoAndSubestacaoId("REDE001", 1);
    }

    @Test
    public void testSaveWithoutSubestacao() {
        // Configuração do objeto RedeMT sem subestação
        redeMT.setSubestacao(null);

        // Execução e verificação da exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> redeMTService.save(redeMT));
        assertEquals("A rede deve estar vinculada a uma subestação antes de ser salva.", exception.getMessage());
    }

    @Test
    public void testUpdate() {
        // Configuração do mock
        when(redeMTRepository.existsById(1)).thenReturn(true);
        when(redeMTRepository.save(redeMT)).thenReturn(redeMT);

        // Execução do método
        RedeMT result = redeMTService.update(1, redeMT);

        // Verificações
        assertNotNull(result);
        assertEquals(redeMT, result);
        assertEquals(1, result.getId());

        // Verifica se os métodos do repositório foram chamados
        verify(redeMTRepository, times(1)).existsById(1);
        verify(redeMTRepository, times(1)).save(redeMT);
    }

    @Test
    public void testUpdateNotFound() {
        // Configuração do mock
        when(redeMTRepository.existsById(1)).thenReturn(false);

        // Execução e verificação da exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> redeMTService.update(1, redeMT));
        assertEquals("RedeMT not found", exception.getMessage());

        // Verifica se o método do repositório foi chamado
        verify(redeMTRepository, times(1)).existsById(1);
    }

    @Test
    public void testDeleteById() {
        // Execução do método
        redeMTService.deleteById(1);

        // Verifica se o método do repositório foi chamado
        verify(redeMTRepository, times(1)).deleteById(1);
    }
}
