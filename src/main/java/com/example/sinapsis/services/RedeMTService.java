package com.example.sinapsis.services;

import com.example.sinapsis.model.RedeMT;
import com.example.sinapsis.repositories.RedeMTRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar operações relacionadas à entidade RedeMT.
 * Encapsula a lógica de negócio e utiliza o RedeMTRepository para interagir com o banco de dados.
 */
@Service
public class RedeMTService {
    @Autowired
    private RedeMTRepository redeMTRepository;

    /**
     * Retorna todas as redes MT cadastradas.
     *
     * @return Lista de redes MT.
     */
    public List<RedeMT> findAll() {
        return redeMTRepository.findAll();
    }

    /**
     * Busca uma rede MT pelo ID.
     *
     * @param id ID da rede MT a ser buscada.
     * @return Rede MT encontrada.
     * @throws RuntimeException Se a rede MT não for encontrada.
     */
    public RedeMT findById(Integer id) {
        return redeMTRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RedeMT not found"));
    }

    /**
     * Salva uma nova rede MT no banco de dados.
     * Verifica se já existe uma rede com o mesmo código na mesma subestação.
     * Garante que a rede está vinculada a uma subestação antes de ser salva.
     *
     * @param redeMT Rede MT a ser salva.
     * @return Rede MT salva.
     * @throws RuntimeException Se já existir uma rede com o mesmo código na mesma subestação ou se a rede não estiver vinculada a uma subestação.
     */
    @Transactional
    public RedeMT save(RedeMT redeMT) {

        // Verifica se a rede já existe dentro da mesma subestação
        if (redeMT.getSubestacao() == null || redeMT.getSubestacao().getId() == null) {
            throw new RuntimeException("A rede deve estar vinculada a uma subestação antes de ser salva.");
        }

        // Verifica se a rede já existe dentro da mesma subestação
        Optional<RedeMT> existente = redeMTRepository.findByCodigoAndSubestacaoId(
                redeMT.getCodigo(),
                redeMT.getSubestacao().getId()
        );

        if (existente.isPresent()) {
            throw new RuntimeException("Rede já cadastrada com esse código para esta subestação: " + redeMT.getCodigo());
        }


        return redeMTRepository.save(redeMT);
    }

    /**
     * Atualiza uma rede MT existente no banco de dados.
     *
     * @param id     ID da rede MT a ser atualizada.
     * @param redeMT Dados atualizados da rede MT.
     * @return Rede MT atualizada.
     * @throws RuntimeException Se a rede MT não for encontrada.
     */
    @Transactional
    public RedeMT update(Integer id, RedeMT redeMT) {
        if (!redeMTRepository.existsById(id)) {
            throw new RuntimeException("RedeMT not found");
        }

        redeMT.setId(id);
        return redeMTRepository.save(redeMT);
    }

    /**
     * Exclui uma rede MT pelo ID.
     *
     * @param id ID da rede MT a ser excluída.
     */
    public void deleteById(Integer id) {
        redeMTRepository.deleteById(id);
    }
}
