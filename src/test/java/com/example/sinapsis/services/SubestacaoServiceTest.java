package com.example.sinapsis.services;


import com.example.sinapsis.model.RedeMT;
import com.example.sinapsis.model.Subestacao;
import com.example.sinapsis.repositories.RedeMTRepository;
import com.example.sinapsis.repositories.SubestacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubestacaoServiceTest {
    @Mock
    private SubestacaoRepository subestacaoRepository;

    @Mock
    private RedeMTRepository redeMTRepository;

    @InjectMocks
    private SubestacaoService subestacaoService;

    private Subestacao subestacao;
    private RedeMT redeMT;

    @BeforeEach
    public void setUp() {
        // Configuração inicial para os testes
        subestacao = new Subestacao();
        subestacao.setId(1);
        subestacao.setCodigo("SUB001");
        subestacao.setNome("Subestação 1");
        subestacao.setLatitude(new BigDecimal("-23.5505"));
        subestacao.setLongitude(new BigDecimal("-46.6333"));

        redeMT = new RedeMT();
        redeMT.setId(1);
        redeMT.setCodigo("REDE001");
        redeMT.setNome("Rede 1");
        redeMT.setSubestacao(subestacao);

        subestacao.setRedesMT(new ArrayList<>(List.of(redeMT)));
    }

    @Test
    public void testFindAll() {
        // Configuração do mock
        when(subestacaoRepository.findAll()).thenReturn(List.of(subestacao));

        // Execução do método
        List<Subestacao> result = subestacaoService.findAll();

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(subestacao, result.get(0));

        // Verifica se o método do repositório foi chamado
        verify(subestacaoRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Configuração do mock
        when(subestacaoRepository.findById(1)).thenReturn(Optional.of(subestacao));

        // Execução do método
        Subestacao result = subestacaoService.findById(1);

        // Verificações
        assertNotNull(result);
        assertEquals(subestacao, result);

        // Verifica se o método do repositório foi chamado
        verify(subestacaoRepository, times(1)).findById(1);
    }

    @Test
    public void testFindByIdNotFound() {
        // Configuração do mock
        when(subestacaoRepository.findById(1)).thenReturn(Optional.empty());

        // Execução e verificação da exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> subestacaoService.findById(1));
        assertEquals("Subestacao not found", exception.getMessage());

        // Verifica se o método do repositório foi chamado
        verify(subestacaoRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Configuração do mock
        when(subestacaoRepository.existsByCodigo("SUB001")).thenReturn(false);
        when(subestacaoRepository.save(subestacao)).thenReturn(subestacao);
        when(redeMTRepository.findByCodigo("REDE001")).thenReturn(Optional.empty());
        when(redeMTRepository.save(redeMT)).thenReturn(redeMT);

        // Execução do método
        Subestacao result = subestacaoService.save(subestacao);

        // Verificações
        assertNotNull(result);
        assertEquals(subestacao, result);

        // Verifica se os métodos do repositório foram chamados
        verify(subestacaoRepository, times(1)).existsByCodigo("SUB001");
        verify(subestacaoRepository, times(2)).save(subestacao);
        verify(redeMTRepository, times(1)).findByCodigo("REDE001");
        verify(redeMTRepository, times(1)).save(redeMT);
    }

    @Test
    public void testSaveWithDuplicateCodigo() {
        // Configuração do mock
        when(subestacaoRepository.existsByCodigo("SUB001")).thenReturn(true);

        // Execução e verificação da exceção
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> subestacaoService.save(subestacao));
        assertEquals("Subestação já cadastrada: SUB001", exception.getMessage());

        // Verifica se o método do repositório foi chamado
        verify(subestacaoRepository, times(1)).existsByCodigo("SUB001");
    }

    @Test
    public void testUpdate() {
        // Configuração do mock
        when(subestacaoRepository.findById(1)).thenReturn(Optional.of(subestacao));
        when(subestacaoRepository.save(subestacao)).thenReturn(subestacao);
        when(redeMTRepository.findByCodigo("REDE001")).thenReturn(Optional.empty());
        when(redeMTRepository.save(redeMT)).thenReturn(redeMT);

        // Execução do método
        Subestacao result = subestacaoService.update(1, subestacao);

        // Verificações
        assertNotNull(result);
        assertEquals(subestacao, result);

        // Verifica se os métodos do repositório foram chamados
        verify(subestacaoRepository, times(1)).findById(1);
        verify(subestacaoRepository, times(1)).save(subestacao);
        verify(redeMTRepository, times(1)).findByCodigo("REDE001");
        verify(redeMTRepository, times(1)).save(redeMT);
    }

    @Test
    public void testUpdateNotFound() {
        // Configuração do mock
        when(subestacaoRepository.findById(1)).thenReturn(Optional.empty());

        // Execução e verificação da exceção
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> subestacaoService.update(1, subestacao));
        assertEquals("Subestação não encontrada: 1", exception.getMessage());

        // Verifica se o método do repositório foi chamado
        verify(subestacaoRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteById() {
        // Execução do método
        subestacaoService.deleteById(1);

        // Verifica se o método do repositório foi chamado
        verify(subestacaoRepository, times(1)).deleteById(1);
    }
}
