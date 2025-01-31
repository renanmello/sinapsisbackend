package com.example.sinapsis.services;

import com.example.sinapsis.model.RedeMT;
import com.example.sinapsis.model.Subestacao;
import com.example.sinapsis.repositories.RedeMTRepository;
import com.example.sinapsis.repositories.SubestacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar operações relacionadas à entidade Subestacao.
 * Encapsula a lógica de negócio e utiliza SubestacaoRepository e RedeMTRepository para interagir com o banco de dados.
 */
@Service
public class SubestacaoService {
    @Autowired
    private SubestacaoRepository subestacaoRepository;

    @Autowired
    private RedeMTRepository redeMTRepository;

    /**
     * Retorna todas as subestações cadastradas.
     *
     * @return Lista de subestações.
     */
    public List<Subestacao> findAll() {
        return subestacaoRepository.findAll();
    }

    /**
     * Busca uma subestação pelo ID.
     *
     * @param id ID da subestação a ser buscada.
     * @return Subestação encontrada.
     * @throws RuntimeException Se a subestação não for encontrada.
     */
    public Subestacao findById(Integer id) {
        return subestacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Subestacao not found"));
    }


    /**
     * Salva uma nova subestação no banco de dados.
     * Verifica se já existe uma subestação com o mesmo código.
     * Processa e associa as redes MT à subestação.
     *
     * @param subestacao Subestação a ser salva.
     * @return Subestação salva.
     * @throws IllegalArgumentException Se já existir uma subestação com o mesmo código.
     */
    @Transactional
    public Subestacao save(Subestacao subestacao) {
        // Verifica se já existe uma subestação com o mesmo código
        if (subestacaoRepository.existsByCodigo(subestacao.getCodigo())) {
            throw new IllegalArgumentException("Subestação já cadastrada: " + subestacao.getCodigo());
        }

        // Salva a subestação primeiro para garantir um ID válido
        Subestacao savedSubestacao = subestacaoRepository.save(subestacao);

        // Processa as RedesMT associadas à Subestação
        List<RedeMT> redesAtualizadas = new ArrayList<>();
        for (RedeMT rede : subestacao.getRedesMT()) {
            Optional<RedeMT> redeExistente = redeMTRepository.findByCodigo(rede.getCodigo());

            if (redeExistente.isPresent()) {
                // Se a rede já existe, associamos à subestação atual
                RedeMT redeAtualizada = redeExistente.get();
                redeAtualizada.setSubestacao(savedSubestacao);
                redesAtualizadas.add(redeMTRepository.save(redeAtualizada));
            } else {
                // Se a rede não existe, criamos e associamos
                rede.setSubestacao(savedSubestacao);
                redesAtualizadas.add(redeMTRepository.save(rede));
            }
        }

        // Atualiza a lista de redes da subestação e salva novamente
        savedSubestacao.setRedesMT(redesAtualizadas);
        return subestacaoRepository.save(savedSubestacao);
    }

    /**
     * Atualiza uma subestação existente no banco de dados.
     * Atualiza os dados básicos da subestação e processa as redes MT associadas.
     *
     * @param id                   ID da subestação a ser atualizada.
     * @param subestacaoAtualizada Dados atualizados da subestação.
     * @return Subestação atualizada.
     * @throws IllegalArgumentException Se a subestação não for encontrada.
     */
    public Subestacao update(Integer id, Subestacao subestacaoAtualizada) {
        // Verifica se a subestação existe
        Subestacao subestacaoExistente = subestacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subestação não encontrada: " + id));

        // Atualiza os dados básicos
        subestacaoExistente.setNome(subestacaoAtualizada.getNome());
        subestacaoExistente.setCodigo(subestacaoAtualizada.getCodigo());
        subestacaoExistente.setLatitude(subestacaoAtualizada.getLatitude());
        subestacaoExistente.setLongitude(subestacaoAtualizada.getLongitude());

        // Processa as RedesMT associadas à Subestação
        List<RedeMT> redesAtualizadas = new ArrayList<>();
        for (RedeMT novaRede : subestacaoAtualizada.getRedesMT()) {
            Optional<RedeMT> redeExistente = redeMTRepository.findByCodigo(novaRede.getCodigo());

            if (redeExistente.isPresent()) {
                // Se a rede já existe, associamos à subestação atual
                RedeMT redeAtualizada = redeExistente.get();
                redeAtualizada.setSubestacao(subestacaoExistente);
                redesAtualizadas.add(redeMTRepository.save(redeAtualizada));
            } else {
                // Se a rede não existe, criamos e associamos
                novaRede.setSubestacao(subestacaoExistente);
                redesAtualizadas.add(redeMTRepository.save(novaRede));
            }
        }

        // Atualiza a lista de redes da subestação
        subestacaoExistente.setRedesMT(redesAtualizadas);

        // Salva e retorna a subestação atualizada
        return subestacaoRepository.save(subestacaoExistente);
    }

    /**
     * Exclui uma subestação pelo ID.
     *
     * @param id ID da subestação a ser excluída.
     */
    public void deleteById(Integer id) {
        subestacaoRepository.deleteById(id);
    }
}