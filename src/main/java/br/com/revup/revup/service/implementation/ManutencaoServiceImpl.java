package br.com.revup.revup.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Manutencao;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.repository.ManutencaoRepository;
import br.com.revup.revup.service.ManutencaoService;

@Service
public class ManutencaoServiceImpl implements ManutencaoService {
    // Repositorios
    @Autowired
    private ManutencaoRepository manutencaoRepository;

    // Métodos auxiliares
    private Manutencao buscarManutencaoPorId (long id) {
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);

        if (manutencao.isEmpty()) 
            throw new RecursoNaoEncontradoException("A manutenção com id " + id + " não encontrada.");

        return manutencao.get();
    }

    // Métodos da interface
    @Override
    public void criarManutencao(Manutencao manutencao) {
        manutencaoRepository.save(manutencao);
    }

    @Override
    public Page<Manutencao> listarManutencoes (Pageable pageable) {
        return manutencaoRepository.findAll(pageable);
    }

    @Override
    public Page<Manutencao> listarManutencoesFiltro(Predicate predicate, Pageable pageable) {
        return manutencaoRepository.findAll(predicate, pageable);
    }

    @Override
    public void editarManutencao(long idManutencao, Manutencao manutencao) {
        Manutencao manutencaoEdicao = buscarManutencaoPorId(idManutencao);

        // Atributos modificados
        manutencaoEdicao.setServico(manutencao.getServico());               //Serviço
        manutencaoEdicao.setValor(manutencao.getValor());                   //Valor
        manutencaoEdicao.setData(manutencao.getData());                     //Data
        manutencaoEdicao.setQuilometragem(manutencao.getQuilometragem());   //Quilometragem

        manutencaoRepository.save(manutencaoEdicao);
    }

    @Override
    public void deletarManutencao(long idManutencao) {
        Manutencao manutencao = buscarManutencaoPorId(idManutencao);

        manutencaoRepository.delete(manutencao);
    }

}
