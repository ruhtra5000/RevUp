package br.com.revup.revup.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.entity.Lavagem;
import br.com.revup.revup.exceptions.RecursoNaoEncontradoException;
import br.com.revup.revup.repository.LavagemRepository;
import br.com.revup.revup.service.LavagemService;

@Service
public class LavagemServiceImpl implements LavagemService {
    // Repositórios
    @Autowired
    private LavagemRepository lavagemRepository;

    // Métodos auxiliares
    private Lavagem buscarLavagemPorId (long id) {
        Optional<Lavagem> lavagem = lavagemRepository.findById(id);

        if (lavagem.isEmpty()) 
            throw new RecursoNaoEncontradoException("A lavagem com id " + id + " não existe.");

        return lavagem.get();
    }

    // Métodos da interface
    @Override
    public void criarLavagem(Lavagem lavagem) {
        lavagemRepository.save(lavagem);
    }

    @Override
    public Page<Lavagem> listarLavagens(Pageable pageable) {
        return lavagemRepository.findAll(pageable);
    }

    @Override
    public Page<Lavagem> listarLavagensFiltro(Predicate predicate, Pageable pageable) {
        return lavagemRepository.findAll(predicate, pageable);
    }

    @Override
    public void editarLavagem(long idLavagem, Lavagem lavagem) {
        Lavagem lavagemEdicao = buscarLavagemPorId(idLavagem);

        // Atributos modificados
        lavagemEdicao.setDetalhes(lavagem.getDetalhes());   //Detalhes
        lavagemEdicao.setData(lavagem.getData());           //Data

        lavagemRepository.save(lavagemEdicao);
    }

    @Override
    public void deletarLavagem(long idLavagem) {
        Lavagem lavagem = buscarLavagemPorId(idLavagem);

        lavagemRepository.delete(lavagem);
    }
    
}
